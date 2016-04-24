package com.lawazia.yourid;

public interface YourIdRepository {
    int addYourInfo(YourInfo sheet);
    YourInfo getYourInfoByMobile(String mobile);
}
