
class UnsafeBankAccount {

    private double balance = 100000;

    public void withdraw(double amount) {

        if (balance >= amount) {

            System.out.println(
                Thread.currentThread().getName()
                + " is processing withdrawal."
            );

            try {
                // Allow another thread to access balance
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            balance -= amount;

            System.out.println(
                Thread.currentThread().getName()
                + " withdrew KSh " + amount
            );

        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class RaceConditionDemo {

    public static void main(String[] args) {

        UnsafeBankAccount account =
            new UnsafeBankAccount();

        Thread A = new Thread(() ->
            account.withdraw(80000), "Customer A");

        Thread B = new Thread(() ->
            account.withdraw(50000), "Customer B");

        A.start();
        B.start();

        try {
            A.join();
            B.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
            "Final balance: KSh " +
            account.getBalance()
        );
    }
}
