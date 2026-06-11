# 🍜 校园美食汇 —— 校园饮食推荐与点评平台

> **课程项目**：《Web应用开发技术》课程期末考核  
> **技术栈**：Spring Boot + JPA + Thymeleaf + Bootstrap + Spring Security

---

## 📋 项目简介

**校园美食汇** 是一个面向高校师生的校园饮食推荐与点评平台，旨在解决校园内食堂窗口、周边小吃信息分散、缺少评分系统的问题。平台提供了店铺浏览、点评发布、点赞收藏、后台管理等完整功能，帮助同学们发现校园美食，分享舌尖体验。

---

## ✨ 功能特性

| 编号 | 功能模块 | 说明 |
|------|---------|------|
| ① | **用户注册/登录** | 支持新用户注册和登录，区分普通用户（USER）和管理员（ADMIN）两种角色 |
| ② | **浏览店铺菜单** | 展示校园及周边店铺信息，支持名称搜索、分类筛选、评分/热度排序、分页浏览 |
| ③ | **发布图文点评** | 1-5星评分 + 文字评价 + 图片上传，提交后需管理员审核通过方可公开 |
| ④ | **点赞/收藏** | AJAX 无刷新操作，一键点赞热门点评、收藏心仪店铺 |
| ⑤ | **后台管理** | 店铺增删改、菜品管理、评论审核（通过/驳回/删除）、用户管理、数据统计 |

### 进阶功能
- ✅ 文件上传（点评配图、店铺图片，UUID 重命名防冲突）
- ✅ 分页查询（店铺列表、点评列表均支持分页）
- ✅ 用户权限管理（Spring Security 角色控制）
- ✅ 数据统计仪表盘（待审核数、已通过数、店铺总数、高分排行）
- ✅ 响应式布局（Bootstrap 5，适配 PC 和移动端）

---

## 🛠️ 技术选型

| 技术领域 | 选型方案 |
|---------|---------|
| 后端框架 | Spring Boot 2.7.18 |
| ORM 框架 | Spring Data JPA（Hibernate） |
| 数据库 | H2 Database（开发/测试，可切换 MySQL） |
| 安全框架 | Spring Security（表单登录 + 角色权限 + CSRF 防护） |
| 模板引擎 | Thymeleaf + Spring Security 标签库 |
| 前端框架 | Bootstrap 5.3 + Bootstrap Icons |
| 前端交互 | 原生 JavaScript（AJAX 异步请求） |
| 构建工具 | Maven |
| JDK 版本 | Java 8+ |

---

## 🗂️ 项目结构

```
campus-food-review/
├── pom.xml                              # Maven 项目配置
├── src/main/java/com/campus/food/
│   ├── CampusFoodApplication.java       # 启动类
│   ├── config/
│   │   ├── SecurityConfig.java          # 安全配置（登录/权限/CSRF）
│   │   ├── WebMvcConfig.java            # 静态资源映射
│   │   └── DataInitializer.java         # 启动时初始化示例数据
│   ├── entity/                          # JPA 实体类（6个）
│   │   ├── User.java                   # 用户（USER/ADMIN 角色）
│   │   ├── Shop.java                   # 店铺（含平均评分、点评数统计）
│   │   ├── MenuItem.java               # 菜品（关联店铺）
│   │   ├── Review.java                 # 点评（PENDING/APPROVED/REJECTED 状态）
│   │   ├── Favorite.java               # 收藏（用户+店铺唯一约束）
│   │   └── ReviewLike.java             # 点赞（用户+点评唯一约束）
│   ├── repository/                      # 数据访问层（6个接口）
│   ├── service/                         # 业务逻辑层（6个类）
│   ├── controller/                      # 请求控制层（6个控制器）
│   └── dto/                            # 数据传输对象（2个）
├── src/main/resources/
│   ├── application.yml                 # 应用配置
│   ├── static/css/style.css            # 自定义样式
│   ├── static/js/main.js               # 前端交互脚本（AJAX）
│   ├── templates/                      # Thymeleaf 页面模板（9个页面）
│   └── uploads/                        # 上传文件存储目录
└── src/test/java/                       # 测试代码
```

---

## 🚀 快速启动

### 环境要求

- JDK 8 或更高版本
- Maven 3.6+（或使用 IDEA 内置 Maven）

### 运行方式

**方式一：IDE 运行（推荐）**

1. 用 IntelliJ IDEA 打开项目根目录
2. 等待 Maven 依赖自动下载完成
3. 运行 `CampusFoodApplication.java` 的 `main` 方法
4. 浏览器访问 `http://localhost:8080`

**方式二：命令行运行**

```bash
cd campus-food-review
mvn spring-boot:run
```

**方式三：打包运行**

```bash
mvn package -DskipTests
java -jar target/campus-food-review-1.0.0.jar
```

---

## 🎯 访问地址

| 页面 | 地址 |
|------|------|
| 🏠 首页 | http://localhost:8080/ |
| 🏪 店铺列表 | http://localhost:8080/shops |
| 👤 登录 | http://localhost:8080/login |
| 📝 注册 | http://localhost:8080/register |
| 👑 管理后台 | http://localhost:8080/admin |
| 🗄️ H2 数据库控制台 | http://localhost:8080/h2-console |

### H2 数据库连接信息

| 字段 | 值 |
|------|-----|
| JDBC URL | `jdbc:h2:mem:campusfood` |
| 用户名 | `sa` |
| 密码 | （留空） |

---

## 👤 预置账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 👑 管理员 | `admin` | `admin123` | 可访问管理后台 |
| 👤 测试用户 | `zhangsan` | `123456` | 可发表点评/点赞/收藏 |
| 👤 测试用户 | `lisi` | `123456` | 同上 |
| 👤 测试用户 | `wangwu` | `123456` | 同上 |

系统启动时会自动初始化 **8 家店铺**、**24 道菜品**、**10 条已审核点评** 和 **5 条收藏记录**，开箱即用。

---

## 📖 使用指南

### 普通用户
1. **浏览店铺**：首页查看高分推荐和热门店铺，或进入店铺列表搜索筛选
2. **查看详情**：点击店铺卡片进入详情页，查看菜品菜单和用户点评
3. **注册/登录**：点击右上角注册或登录
4. **写点评**：在店铺详情页点击"写点评"，评分 + 写评价 + 可上传图片
5. **点赞/收藏**：在点评下方点赞，在店铺详情页收藏店铺
6. **个人中心**：查看自己的点评记录和收藏列表

### 管理员
1. 使用 `admin / admin123` 登录
2. 点击右上角用户菜单 → **管理后台**
3. **仪表盘**：查看平台统计数据
4. **店铺管理**：添加/编辑/删除店铺，管理菜品菜单
5. **评论审核**：查看待审核点评，通过/驳回/删除

---

## 🧱 数据库设计

系统共包含 6 张数据表，实体关系如下：

```
User ────→ Review        （一对多：一个用户可有多条点评）
User ────→ Favorite      （一对多：一个用户可有多个收藏）
User ────→ ReviewLike    （一对多：一个用户可有多个点赞）
Shop ────→ Review        （一对多：一个店铺可有多条点评）
Shop ────→ MenuItem      （一对多：一个店铺可有多个菜品）
Shop ────→ Favorite      （一对多：一个店铺可被多个用户收藏）
Review ──→ ReviewLike    （一对多：一条点评可有多个点赞）
```

### 关键设计

- **冗余统计字段**：Shop 实体中 `avgRating` 和 `reviewCount` 在每次点评审核通过时同步更新，避免实时计算，提升查询性能
- **唯一约束**：Favorite 和 ReviewLike 使用数据库唯一约束防止重复操作
- **状态枚举**：Review 的 `status` 字段控制点评生命周期（PENDING → APPROVED / REJECTED）

---

## 🔒 安全机制

| 安全措施 | 实现方式 |
|---------|---------|
| 密码加密 | BCryptPasswordEncoder（自动加盐） |
| 角色权限 | Spring Security URL 拦截 + 角色判断 |
| CSRF 防护 | 默认开启，表单使用 `th:action` 自动注入 Token |
| 会话管理 | 基于 Cookie + Session，30 分钟超时 |
| 上传限制 | 单文件最大 10MB，总请求最大 20MB |

---

## 📸 页面截图

| 页面 | 功能 |
|------|------|
| **首页** | 搜索栏、高分店铺推荐、热门店铺、热门点评 |
| **店铺列表** | 搜索、分类筛选、排序、分页 |
| **店铺详情** | 店铺信息、菜品菜单、用户点评、收藏按钮 |
| **点评发布** | 1-5 星评分、文字内容、图片上传 |
| **个人中心** | 我的点评、我的收藏、审核状态标签 |
| **管理后台** | 仪表盘统计、店铺管理、评论审核、用户管理 |

---

## 🐛 常见问题

### Q: 启动报错 "程序包 lombok 不存在"？
A: 本项目已移除 Lombok 依赖，所有实体类使用手动编写的 Getter/Setter/Builder，不会出现此问题。如果 IDE 仍有报错，尝试 `File → Invalidate Caches → Invalidate and Restart`。

### Q: 店铺详情页报 500 错误？
A: 检查 `application.yml` 中 `spring.jpa.open-in-view` 是否为 `true`。这是解决 Hibernate 懒加载异常的关键配置。

### Q: 登录提交后报 403？
A: Spring Security 默认开启 CSRF 保护。请确认表单使用了 `th:action="@{/login}"` 而非 `action="/login"`。

### Q: H2 控制台无法访问？
A: 确认 `spring.h2.console.enabled=true` 且 SecurityConfig 中 `frameOptions().disable()` 已配置。

---

## 🔮 后续扩展方向

- [ ] 接入 MySQL 数据库实现数据持久化
- [ ] 引入 Redis 缓存热点数据，提升性能
- [ ] 增加用户头像上传和个人信息编辑
- [ ] 使用 ECharts 实现数据可视化（评分分布、月度趋势）
- [ ] 引入 Elasticsearch 实现全文搜索
- [ ] 部署到云服务器实现公网访问

---

## 📄 许可证

本项目为课程设计作品，仅供学习参考。
