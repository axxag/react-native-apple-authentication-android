# react-native-apple-authentication-android

## Getting started

`$ npm install react-native-apple-authentication-android --save`

### Mostly automatic installation

`$ react-native link react-native-apple-authentication-android`

## Usage

```javascript
import AppleAuthenticationAndroid,
{NOT_CONFIGURED_ERROR, SIGNIN_FAILED_ERROR, SIGNIN_CANCELLED_ERROR}
from 'react-native-apple-authentication-android';

### Initialize the module
AppleAuthenticationAndroid.configure({
	clientId: 'Your client ID',
  redirectUri: 'Your redirect URI',
	scope: 'Preferred scope'; // defaults to 'email name'
})

### Sign In with Apple
const signInWithApple = async () => {
	try {
		const authorizationCode = await RNAppleAuthAndroid.signIn()
		console.log('Got auth code', authorizationCode)
	} catch (error) {
		if (error && error.message)
			switch (error.message) {
				case NOT_CONFIGURED_ERROR:
					console.log('RNAppleAuthAndroid not configured yet.')
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


```
