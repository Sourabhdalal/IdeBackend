from django.contrib import admin
from django.urls import path
from .views import greeting, SubmissionViewSet, UserViewSet, LoginView, register
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register("user", UserViewSet)
router.register("submit", SubmissionViewSet)

urlpatterns = [
    path("", greeting, name=""),
    path("login/", LoginView.as_view()),
    path("register/", register)
]

urlpatterns += router.urls