package com.reactlibrary;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.willowtreeapps.signinwithapplebutton.SignInWithAppleCallback;
import com.willowtreeapps.signinwithapplebutton.SignInWithAppleConfiguration;
import com.willowtreeapps.signinwithapplebutton.SignInWithAppleResult;
import com.willowtreeapps.signinwithapplebutton.SignInWithAppleService;

import java.util.HashMap;
import java.util.Map;

public class AppleAuthenticationAndroidModule extends ReactContextBaseJavaModule {

    private static final String E_NOT_CONFIGURED_ERROR = "E_NOT_CONFIGURED_ERROR";
    private static final String E_SIGNIN_FAILED_ERROR = "E_SIGNIN_FAILED_ERROR";
    private static final String E_SIGNIN_CANCELLED_ERROR = "E_SIGNIN_CANCELLED_ERROR";

    private final ReactApplicationContext reactContext;

    private @Nullable SignInWithAppleConfiguration configuration;

    public AppleAuthenticationAndroidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AppleAuthenticationAndroid";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(E_NOT_CONFIGURED_ERROR, E_NOT_CONFIGURED_ERROR);
        constants.put(E_SIGNIN_FAILED_ERROR, E_SIGNIN_FAILED_ERROR);
        constants.put(E_SIGNIN_CANCELLED_ERROR, E_SIGNIN_CANCELLED_ERROR);
        return constants;
    }

    private @Nullable FragmentManager getFragmentManagerHelper() {
        Activity activity = getCurrentActivity();
        if (activity == null || !(activity instanceof FragmentActivity)) {
            return null;
        }

        return ((FragmentActivity) activity).getSupportFragmentManager();
    }

    @ReactMethod
    public void configure(ReadableMap configObject) {
        String clientId = "";
        String redirectUri = "";
        String scope = "email name";

        if (configObject.hasKey("clientId")) { 
            clientId = configObject.getString("clientId");
        }

        if (configObject.hasKey("redirectUri")) { 
            redirectUri = configObject.getString("redirectUri");
        }

        if (configObject.hasKey("scope")) { 
            scope = configObject.getString("scope");
        }

        this.configuration = new SignInWithAppleConfiguration.Builder()
            .clientId(clientId)
            .redirectUri(redirectUri)
            .scope(scope)
            .build();
    }

    @ReactMethod
    public void signIn(final Promise promise) {
        if (this.configuration == null) {
            promise.reject(E_NOT_CONFIGURED_ERROR);
            return;
        }
        FragmentManager fragmentManager = this.getFragmentManagerHelper();
        
        if (fragmentManager == null) {
            promise.reject(E_NOT_CONFIGURED_ERROR);
            return;
        }


        SignInWithAppleCallback callback = new SignInWithAppleCallback() {
            @Override
            public void onSignInWithAppleSuccess(@NonNull String authorizationCode) {
                promise.resolve(authorizationCode);
            }

            @Override
            public void onSignInWithAppleFailure(@NonNull Throwable error) {
                promise.reject(E_SIGNIN_FAILED_ERROR, error);
            }

            @Override
            public void onSignInWithAppleCancel() {
                promise.reject(E_SIGNIN_CANCELLED_ERROR);
            }
        };

        String fragmentTag = "SignInWithAppleButton-$id-SignInWebViewDialogFragment";
        SignInWithAppleService service = new SignInWithAppleService(
            fragmentManager,
            fragmentTag,
            configuration,
            callback
        );

        service.show();
    }
}
