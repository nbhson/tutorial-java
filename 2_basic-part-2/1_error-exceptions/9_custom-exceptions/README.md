# Custom Exceptions (Ngoại lệ tự định nghĩa) — throw & throws

> Bài này gốc là bài về `throw`/`throws`, nhưng trọng tâm là **tự tạo exception của riêng bạn**. Bạn xem thêm cách dùng cơ bản của `throw`/`throws` trong bài `1_exceptions-introduce`.

## Exception trong Java là gì?

`Exception` là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn, chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

## Custom Exception là gì?

`Custom Exception` (ngoại lệ tự định nghĩa) là việc bạn **tạo ra lớp exception của riêng mình** để mô tả đúng lỗi của bài toán. Các exception tùy chỉnh thường kế thừa từ lớp `Exception`.

## Khi nào cần custom exception?

> - Để **phân loại exception** theo từng loại lỗi riêng trong dự án của bạn.
> - Để cho phép **xử lý exception ở cấp ứng dụng** (bắt đúng loại lỗi nghiệp vụ, ví dụ "tài khoản không đủ tiền").

## Tạo custom exception trong Java

### Cú pháp

Chỉ cần `extends` lớp `Exception` có sẵn để tạo exception của riêng bạn. **Đây được coi là checked exception.**

```java
class MyException extends Exception {
}
```

### Quy tắc tạo custom exception

- Mọi exception phải là con (trực tiếp hoặc gián tiếp) của `Throwable`.
- Nếu muốn tạo **checked exception** (bắt buộc phải `handle` hoặc `declare` theo Handle-or-Declare Rule), hãy kế thừa lớp `Exception`.
- Nếu muốn tạo **runtime exception** (không bị compiler ép xử lý), hãy kế thừa lớp `RuntimeException`.

### Ví dụ 1 — kế thừa `Exception` (checked, theo quy tắc Handle-or-Declare)

```java
package list;

public class Main {
	public static void main(String args[]) {
		CheckingAccount c = new CheckingAccount();
		System.out.println("Depositing $500...");
		c.deposit(500.00);

		try { // question - different between Exception & Runtime
			System.out.println("\nWithdrawing $100...");
			c.withdraw(100.00);
			System.out.println("\nWithdrawing $600...");
			c.withdraw(600.00);
		} catch (InsufficientFundsException e) {
			System.out.println("Sorry, but you are short $" + e.getAmount());
			e.printStackTrace();
		}
	}

}

class CheckingAccount {
	private double balance;

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) throws InsufficientFundsException {
		if (amount <= balance) {
			balance -= amount;
		} else {
			double needs = amount - balance;
			throw new InsufficientFundsException(needs);
		}
	}
}

class InsufficientFundsException extends Exception {
	private double amount;

	public InsufficientFundsException(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
}
```

Trong ví dụ này:

- `withdraw()` khai báo `throws InsufficientFundsException` — nơi gọi **bắt buộc** phải bắt nó trong `try/catch` (Handle) hoặc khai báo tiếp `throws` (Declare).
- `InsufficientFundsException` mang thêm dữ liệu riêng (`amount`) — điều mà exception có sẵn không làm được.

### Ví dụ 2 — kế thừa `RuntimeException` (unchecked)

```java
package com.tutorialspoint;

public class BankDemo {

   public static void main(String [] args) {
      CheckingAccount c = new CheckingAccount(101); // question - different between Exception & Runtime
      System.out.println("Depositing $500...");
      c.deposit(500.00);
      
      System.out.println("\nWithdrawing $100...");
      c.withdraw(100.00);
      System.out.println("\nWithdrawing $600...");

   }
}

class CheckingAccount {
   private double balance;
   private int number;
   
   public CheckingAccount(int number) {
      this.number = number;
   }
   
   public void deposit(double amount) {
      balance += amount;
   }
   
   public void withdraw(double amount) {
      if(amount <= balance) {
         balance -= amount;
      }else {
         double needs = amount - balance;
         throw new InsufficientFundsException(needs);
      }
   }
   
   public double getBalance() {
      return balance;
   }
   
   public int getNumber() {
      return number;
   }
}

class InsufficientFundsException extends RuntimeException {
   private double amount;
   
   public InsufficientFundsException(double amount) {
      this.amount = amount;
   }
   
   public double getAmount() {
      return amount;
   }
}
```

Ở đây `InsufficientFundsException` kế thừa `RuntimeException`, nên `withdraw()` **không cần khai báo `throws`** và nơi gọi cũng không bắt buộc phải `try/catch`. Compiler không kiểm tra — nhưng chương trình vẫn dừng nếu exception không được bắt.

## So sánh hai cách

| Tiêu chí | `extends Exception` | `extends RuntimeException` |
|----------|--------------------|---------------------------|
| Loại | Checked | Unchecked |
| Compiler ép xử lý? | ✅ Bắt buộc `try/catch` hoặc `throws` | ❌ Không |
| Phù hợp khi | Lỗi nghiệp vụ mà nơi gọi nên/không thể bỏ qua | Lỗi lập trình hoặc lỗi không mong muốn lúc chạy |
