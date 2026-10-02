package RateLimiter;

public class RateLimiterDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== API RATE LIMITER DRIVER SIMULATION ===");
        RateLimiterService service = new RateLimiterService();

        service.registerClient("ClientA", "token_bucket", 2.0, 1.0);
        service.registerClient("ClientB", "sliding_window", 2.0, 1000.0);

        System.out.println("\n--- ClientA (Token Bucket) Burst test ---");
        System.out.println("Req 1: " + (service.isAllowed("ClientA") ? "ALLOWED" : "BLOCKED"));
        System.out.println("Req 2: " + (service.isAllowed("ClientA") ? "ALLOWED" : "BLOCKED"));
        System.out.println("Req 3: " + (service.isAllowed("ClientA") ? "ALLOWED" : "BLOCKED"));

        System.out.println("Waiting 1.1 seconds for refill...");
        Thread.sleep(1100);
        System.out.println("Req 4 (after wait): " + (service.isAllowed("ClientA") ? "ALLOWED" : "BLOCKED"));

        System.out.println("\n--- ClientB (Sliding Window) test ---");
        System.out.println("Req 1: " + (service.isAllowed("ClientB") ? "ALLOWED" : "BLOCKED"));
        System.out.println("Req 2: " + (service.isAllowed("ClientB") ? "ALLOWED" : "BLOCKED"));
        System.out.println("Req 3: " + (service.isAllowed("ClientB") ? "ALLOWED" : "BLOCKED"));

        System.out.println("\n--- Fail-Safe Mode Preservation test ---");
        System.out.println("Unregistered client request (Fail-Open): " + (service.isAllowed("ClientUnknown") ? "ALLOWED" : "BLOCKED"));
        service.setFailOpen(false);
        System.out.println("Unregistered client request (Fail-Closed): " + (service.isAllowed("ClientUnknown") ? "ALLOWED" : "BLOCKED"));
    }
}
