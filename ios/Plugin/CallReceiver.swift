import Foundation

@objc public class CallReceiver: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
