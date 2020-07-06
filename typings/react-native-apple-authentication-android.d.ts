declare module "react-native-apple-authentication-android" {
  export const NOT_CONFIGURED_ERROR: string;
  export const SIGNIN_FAILED_ERROR: string;
  export const SIGNIN_CANCELLED_ERROR: string;

  interface IRNAppleAuthAndroidConfig {
    clientId: string;
    redirectUri: string;
    scope?: string;
  }
  interface IRNAppleAuthAndroid {
    configure(configObject: IRNAppleAuthAndroidConfig): void;
    signIn(): Promise<string>;
  }
  const RNAppleAuthAndroid: IRNAppleAuthAndroid;
  export default RNAppleAuthAndroid;
}
