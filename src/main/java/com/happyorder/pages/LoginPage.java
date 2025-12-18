package com.happyorder.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.happyorder.base.BasePages;

public class LoginPage extends BasePages{
  public LoginPage(WebDriver driver) {
		super(driver);
	}

    //Lay Xpath 
    public By txtTenDangNhap = By.xpath("//input[@name='username']");
    public By txtMatKhau = By.xpath("//input[@name='password']");
    public By btnDangNhap = By.xpath("//span[@class='w-100']");
    

    //Nhap du lieu
    public void inputTenDangNhap(String tenDangNhap)throws InterruptedException{
       System.out.println("Dang tim element username...");
       waitForElementVisible(txtTenDangNhap);
       System.out.println("Da tim thay element username, dang nhap du lieu...");
       sendKeys(txtTenDangNhap, tenDangNhap);
       System.out.println("Da nhap xong username: " + tenDangNhap);
}

    public void inputMatKhau(String matKhau)throws InterruptedException{
        System.out.println("Dang tim element username...");
        waitForElementVisible(txtMatKhau);
        System.out.println("Da tim thay element username, dang nhap du lieu...");
        sendKeys(txtMatKhau, matKhau);
        System.out.println("Da nhap xong username: " + matKhau);
    }

    public void clickbtnDangNhap() {
        try {
            System.out.println("Trying to find submit button...");
            click(btnDangNhap);
            System.out.println("Clicked submit button successfully");
        } catch (Exception e) {
            System.out.println("Button not found with xpath, trying to submit form...");
            // Neu khong tim thay button, thu submit form truc tiep
			driver.findElement(txtMatKhau).submit();
			System.out.println("Submitted form via password field");
        }
    }

    //Dóng gói toàn bộ quy trình đăng nhập vào một thao tác duy nhất
	public void login(String user, String password) throws InterruptedException{
		if (user != null) {
	        inputTenDangNhap(user);
	    }
	    if (password != null) {
	        inputMatKhau(password);
	    }
	}

    //TC: ❌ Nhập sai user/password
	public String getThongBaoSaiData() {
		By toastLocator = By.xpath("//div[@class='ant-message']");
		return getToastMessageText(toastLocator, 5);
	}
}