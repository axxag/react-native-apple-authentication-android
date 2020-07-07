# react-native-apple-authentication-android

## Getting started

`$ yarn add react-native-apple-authentication-android`

## Service setup

First, follow Apple's instructions to set up Sign In with Apple [in your iOS app](https://help.apple.com/developer-account/#/devde676e696) and [for a web service](https://help.apple.com/developer-account/#/dev1c0e25352). It is the web service setup that you'll use from Android, but you need both.

    More setup is necessary for backend operations, but the above is all you need to use this library. For more detail, you can read Aaron Parecki's walkthrough, What the Heck is Sign In with Apple?

You should have created:

    An App ID
        including the Sign In with Apple capability
    A Service ID
        using the App ID as its primary
        mapped to a domain you control
            which Apple has verified
        including at least one Return URL

From this setup, you will need two OAuth arguments to use this library:

    A client ID, which you entered as the Identifier field of the Service ID.
    A redirect URI, which you entered as the Return URL.

    We recommend you use an https:// address for your redirect URI. If you use an http:// address, you may need to include a security configuration to allow cleartext traffic. Although this library should intercept the redirect request, you should regard this as a less secure option. If it's necessary, see the Network security configuration documentation for instructions on setting up a security configuration. Add that file to your Manifest's <application> tag using the attribute android:android:networkSecurityConfig.

## Usage

```javascript
import AppleAuthenticationAndroid,
  {
    NOT_CONFIGURED_ERROR,
    SIGNIN_CANCELLED_ERROR,
    SIGNIN_FAILED_ERROR,
    ResponseType,
    Scope,
  }
from 'react-native-apple-authentication-android';

// Initialize the module
AppleAuthenticationAndroid.configure({
  clientId: 'Your client ID',
  redirectUri: 'Your redirect URI',
  
  // [OPTIONAL]
  // Scope.ALL (DEFAULT) = 'email name'
  // Scope.Email = 'email';
  // Scope.Name = 'name';  
  scope: Scope.ALL,
  
  // [OPTIONAL]
  // ResponseType.ALL (DEFAULT) = 'code id_token'; 
  // ResponseType.CODE = 'code';
  // ResponseType.ID_TOKEN = 'id_token';
  responseType: ResponseType.ALL, 
})

// Sign In with Apple
const signInWithApple = async () => {
  try {
    const response = await AppleAuthenticationAndroid.signIn()
    if (response) {
      const code = response.code // Present if selected ResponseType.ALL / ResponseType.CODE
      const id_token = response.id_token // Present if selected ResponseType.ALL / ResponseType.ID_TOKEN
      console.log('Got auth code', code)
      console.log('Got id_token', id_token)
    }
  } catch (error) {
    if (error && error.message) {
      switch (error.message) {
        case NOT_CONFIGURED_ERROR:
          console.log('AppleAuthenticationAndroid not configured yet.')
        break
        case SIGNIN_FAILED_ERROR:
          console.log('Apple signin failed.')
        break
        case SIGNIN_CANCELLED_ERROR:
          console.log('User cancelled apple signin.')
        break

        default:
        break;
      }
    }
  }
}


```

## Credits

This library is based on https://github.com/willowtreeapps/sign-in-with-apple-button-android. As such, it shares its dependencies and some setup instructions.
