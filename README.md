# 图书管理系统

> 面向管理员

## 板块

### 1.登录注册

### 2.书籍管理

### 3.用户管理

### 4.借阅记录

### 5.日志记录

## 数据库设计
### 1.书籍表(books)
| 字段名       | 数据类型         | 描述             |
|-----------|--------------|----------------|
| id        | int          | 主键，自增          |
| book_name | varchar(25)  | 书名             |
| author    | varchar(100) | 作者             |
| category  |varchar(50)| 类别             |
| status    |varchar(20)| 状态(可借/已借出/维护等) |

### 2.用户表(users)
| 字段名      | 数据类型         | 描述             |
|----------|--------------|----------------|
| id       | int          | 主键，自增          |
| username | varchar(50)  | 用户名            |
| password | varchar(255) | 密码(md5加密存储)    |
| role_id  | int          | 外键,关联角色表       |

### 3.角色表(roles)
| 字段名    | 数据类型         | 描述          |
|--------|--------------|-------------|
| id     | int          | 主键，自增       |
| name   | varchar(50)  | 角色名(管理员/用户) |

### 4.借阅记录表(borrow_records)
| 字段名         | 数据类型          | 描述           |
|-------------|---------------|--------------|
| id          | int           | 主键，自增        |
| book_id     | int           | 外键,关联书籍表     |
| user_id     | int           | 外键,关联用户表     |
| borrow_date | timestamp     | 借阅时间         |
| return_date | timestamp     | 归还时间(可空)     |


## 创表语句
```mysql
角色
CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL
);
用户
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role_id INT,
                       FOREIGN KEY (role_id) REFERENCES roles(id)
);
书籍
CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(100),
                       category VARCHAR(50),
                       status INT NOT NULL, -- 0表示可借，1表示已借出，等等
                       is_deleted INT DEFAULT 0 -- 0表示未删除，1表示已删除
);
借阅表
CREATE TABLE borrow_records (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                book_id INT,
                                user_id INT,
                                borrow_date TIMESTAMP NOT NULL,
                                return_date TIMESTAMP NULL,
                                status INT NOT NULL, -- 0表示借出，1表示已归还，等等
                                is_deleted INT DEFAULT 0, -- 0表示未删除，1表示已删除
                                FOREIGN KEY (book_id) REFERENCES books(id),
                                FOREIGN KEY (user_id) REFERENCES users(id)
);

```

## API

#### Book

`/books/{id}`

> 根据id查询书籍

| `GET`  |       发送       |        回复         |
| :----: | :--------------: | :-----------------: |
| header | `token：jwt令牌` |                     |
|  body  |                  | `{"message": "OK"}` |

> 根据id删除书籍