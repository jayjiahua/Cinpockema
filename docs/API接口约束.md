# API接口约束

## 1. 基本说明
- 需要实现基于 HTTP/JSON 的 RESTful API

- HTTP头部必须包含```"Content-Type: application/json"```，否则会返回**415**错误

- 用户认证采用HTTP Basic Authentication，现在支持的认证方式有两种
    1. 用户名 + 密码

    ```
    username:password@127.0.0.1/api/xxx
    ```

    2. token (可通过登录操作获得，安全角度考虑推荐使用此方式)
    ```
    token:@127.0.0.1/api/xxx
    ```

## 2. 目录
* [用户与会话](#user)
  * [user](#user)
  * [session](#session)


# 用户与会话

<h2 id="user">用户 (user)</h2>

<h2 id="session">会话 (sessinon)</h2>
