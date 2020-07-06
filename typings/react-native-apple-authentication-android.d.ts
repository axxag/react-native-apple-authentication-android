declare module "react-native-apple-authentication-android" {
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
