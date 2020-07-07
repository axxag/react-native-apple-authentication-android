import { NativeModules } from "react-native";

const { AppleAuthenticationAndroid } = NativeModules;

export const NOT_CONFIGURED_ERROR =
  AppleAuthenticationAndroid.E_NOT_CONFIGURED_ERROR;
export const SIGNIN_FAILED_ERROR =
  AppleAuthenticationAndroid.E_SIGNIN_FAILED_ERROR;
export const SIGNIN_CANCELLED_ERROR =
  AppleAuthenticationAndroid.E_SIGNIN_CANCELLED_ERROR;

export const Scope = AppleAuthenticationAndroid.Scope;
export const ResponseType = AppleAuthenticationAndroid.ResponseType;

export default AppleAuthenticationAndroid;
