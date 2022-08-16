from allauth.account.adapter import DefaultAccountAdapter

class CustomAccountAdapter(DefaultAccountAdapter):
    def save_user(self, request, user, form, commit=True):
        data = form.cleaned_data
        user = super().save_user(request, user, form, False)
        nickname = data.get('nickname')
        birthday = data.get('birthday')
        if nickname:
            user.nickname = nickname
        if birthday:
            user.birthday = birthday

        user.save()
        return user
