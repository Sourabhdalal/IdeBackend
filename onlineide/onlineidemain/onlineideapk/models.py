from django.db import models
from django.contrib.auth.models import User
# Create your models here.




class Submission(models.Model):
    ACCEPTANCE_STATUS = [
        ("S", "SUCCESS"),
        ("E", "ERROR"),
        ("P", "PENDING")
    ]

    code = models.CharField(max_length=2000)
    language = models.CharField(max_length=100)
    submission_time = models.DateTimeField(auto_now=True)
    status = models.CharField(max_length=1, choices=ACCEPTANCE_STATUS)
    user_input = models.CharField(max_length=200, blank=True)
    output = models.CharField(max_length=200, blank=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)

