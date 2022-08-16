from django.db import models
from django.utils import timezone
from django.conf import settings


class Memory(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='memories', on_delete=models.CASCADE)
    date = models.DateTimeField(default=timezone.now)
    body = models.CharField(max_length=200)

# Create your models here.
