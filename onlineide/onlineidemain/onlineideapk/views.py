from django.shortcuts import render
from django.http import HttpResponse
from rest_framework.viewsets import ModelViewSet
from .models import User, Submission
from .serializer import SubmissionSerializer, UserSerializer
from .utils import create_code_file, execute_file
from rest_framework import permissions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from django.contrib.auth import login
from rest_framework.authtoken.serializers import AuthTokenSerializer
from knox.views import LoginView as KnoxLoginView
import multiprocessing as mp
from multiprocessing import process
# Create your views here.


def greeting(request):
    return HttpResponse("Welcome to Online Ide !")


class LoginView(KnoxLoginView):
    permission_classes = (permissions.AllowAny,)

    def post(self, request, format = None):
        serializer = AuthTokenSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data['user']
        login(request, user)
        return super(LoginView, self).post(request, format=None)


@api_view(http_method_names=["post"])
@permission_classes((permissions.AllowAny ,))
def register(request):
    serializer = UserSerializer(data=request.data)
    serializer.is_valid(raise_exception=True)
    user = serializer.save()
    return Response(UserSerializer(user).data , status=201)


class UserViewSet(ModelViewSet):
    serializer_class = UserSerializer
    permission_classes = (permissions.IsAuthenticated,)
    queryset = User.objects.all()

    def list(self, request, *args, **kwargs):
        return Response(UserSerializer(request.user).data, status=200)


class SubmissionViewSet(ModelViewSet):
    queryset = Submission.objects.all()
    serializer_class = SubmissionSerializer
    permission_classes = (permissions.IsAuthenticated ,)

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        queryset = queryset.filter(user=request.user)

        return Response(self.get_serializer(queryset, many=True).data, status = 200)


    def create(self, request, *args, **kwargs):

        request.data["status"] = "P"
        request.data["user"] = request.user.pk
        file_name = create_code_file(request.data.get("code"), request.data.get("language"))
        serializer = SubmissionSerializer(data = request.data)
        serializer.is_valid(raise_exception=True)
        submission = serializer.save()

        p = mp.Process(target = execute_file,
                    args=(file_name, request.data.get("language"), submission.pk))
        p.start()
        return Response({
            "message" : "Submitted Succesfully"
        }, status=200)

