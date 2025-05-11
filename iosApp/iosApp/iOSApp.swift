import SwiftUI
import GoogleSignIn
import Firebase
import shared
import FirebaseCore
import FirebaseMessaging
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea()
                .onOpenURL { url in
                    print("Received URL in onOpenURL: \(url)")
                    
                    if GIDSignIn.sharedInstance.handle(url) { return }
                    
                    guard let components = URLComponents(url: url, resolvingAgainstBaseURL: true),
                          let queryItems = components.queryItems else { return }
                    
                    let success = queryItems.first(where: { $0.name == "success" })?.value == "true"
                    let cancel = queryItems.first(where: { $0.name == "cancel" })?.value == "true"
                    let token = queryItems.first(where: { $0.name == "token" })?.value
                    
                    print(
                        """
                            ✅ Success: \(success)
                            ✅ Cancel: \(cancel)
                            ✅ Token: \(token ?? "null")
                        """
                    )
                    
                    PreferencesRepository().savePayPalData(
                        isSuccess: success ? KotlinBoolean(value: true) : nil,
                        error: cancel ? "Payment canceled." : nil,
                        token: token
                    )
                    // IntentHandlerHelper().navigateToPaymentCompleted(
                    //   isSuccess: success ? KotlinBoolean(true) : nil,
                    //   error: cancel ? "Payment canceled." : nil,
                    //   token: token
                    // )
                }
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos(
                    showPushNotification: true,
                    askNotificationPermissionOnStart: true,
                    notificationSoundName: nil
                  )
              )
        return true
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
           Messaging.messaging().apnsToken = deviceToken
     }
}
