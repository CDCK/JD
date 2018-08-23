package com.wind.administrator.fuck.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 *
 */

public class UserDao {
    //获取
    public Account getLoginUser() {
        List<Account> all = DataSupport.findAll(Account.class);
        return !all.isEmpty()?all.get(0):null;
    }
    //保存
    public boolean saveUser(Account account) {
        return account.save();
    }
    //清除
    public void clearAll() {
        DataSupport.deleteAll(Account.class);
    }
}
