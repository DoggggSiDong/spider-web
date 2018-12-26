package spider.web.resource;

import org.springframework.stereotype.Component;
import spder.task.Account;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
@Component
public class AccountResource {
    private final ArrayBlockingQueue<Account> accountPool;
    public AccountResource(){
        accountPool = new ArrayBlockingQueue(2);
    }
    @PostConstruct
    public void init() throws InterruptedException {
        Account account1= new Account();
        account1.setAccount("1");
        Account account2 = new Account();
        account2.setAccount("2");
        accountPool.put(account1);
        accountPool.put(account2);
    }
    public Account getAccount() throws InterruptedException {
        return accountPool.take();
    }
    public void returnAccount(Account account) throws InterruptedException {
        accountPool.put(account);
    }
}