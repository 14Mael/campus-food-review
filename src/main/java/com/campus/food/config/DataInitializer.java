package com.campus.food.config;

import com.campus.food.entity.*;
import com.campus.food.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        // Create admin
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .nickname("管理员")
                .role(User.Role.ADMIN)
                .build();
        userRepository.save(admin);

        // Create test users
        User user1 = User.builder()
                .username("zhangsan")
                .password(passwordEncoder.encode("123456"))
                .nickname("张三")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .username("lisi")
                .password(passwordEncoder.encode("123456"))
                .nickname("李四")
                .build();
        userRepository.save(user2);

        User user3 = User.builder()
                .username("wangwu")
                .password(passwordEncoder.encode("123456"))
                .nickname("王五")
                .build();
        userRepository.save(user3);

        // Create shops
        Shop shop1 = createShop("第一食堂·麻辣烫窗口", "第一食堂二楼东侧", "中餐",
                "招牌麻辣烫，新鲜食材自选，汤底浓郁，深受同学们喜爱。");
        Shop shop2 = createShop("第二食堂·兰州拉面", "第二食堂一楼北侧", "快餐",
                "正宗兰州拉面，手工拉制，面条劲道，汤鲜味美。");
        Shop shop3 = createShop("咖啡时光·校园店", "图书馆一楼", "饮品",
                "现磨咖啡、鲜榨果汁、精致甜点，适合学习小憩。");
        Shop shop4 = createShop("北门烧烤一条街·阿强烧烤", "北门外50米", "小吃",
                "网红烧烤店，烤串、烤鱼、烤蔬菜，夜宵首选。");
        Shop shop5 = createShop("学子餐厅·自选窗口", "第三食堂三楼", "中餐",
                "自选套餐，荤素搭配，每日更新菜单，价格实惠。");
        Shop shop6 = createShop("甜蜜时光·烘焙坊", "商业街C座", "甜品",
                "手工烘焙，蛋糕面包，新鲜出炉，甜而不腻。");
        Shop shop7 = createShop("西餐·遇见牛排", "商业街A座2楼", "西餐",
                "厚切牛排，意面披萨，浪漫用餐环境。");
        Shop shop8 = createShop("水果茶·一点点", "北门商业街", "饮品",
                "新鲜水果茶、奶茶、奶盖，学生最爱饮品店。");

        // Create menu items
        createMenuItems(shop1, new String[][]{
                {"招牌麻辣烫", "12.00", "自选食材，称重计价"},
                {"骨汤米线", "10.00", "浓郁骨汤底"},
                {"酸辣粉", "8.00", "正宗重庆口味"},
        });
        createMenuItems(shop2, new String[][]{
                {"牛肉拉面", "12.00", "大份牛肉拉面"},
                {"炒刀削面", "10.00", "配时蔬"},
                {"羊肉泡馍", "15.00", "西北风味"},
        });
        createMenuItems(shop3, new String[][]{
                {"美式咖啡", "15.00", "现磨美式"},
                {"拿铁", "18.00", "意式拿铁"},
                {"抹茶蛋糕", "12.00", "日式抹茶"},
        });
        createMenuItems(shop4, new String[][]{
                {"烤羊肉串", "3.00", "每串"},
                {"烤鸡翅", "5.00", "每串"},
                {"烤茄子", "8.00", "整条"},
        });
        createMenuItems(shop5, new String[][]{
                {"红烧肉套餐", "15.00", "配米饭+汤"},
                {"鱼香肉丝套餐", "13.00", "配米饭+汤"},
                {"宫保鸡丁套餐", "13.00", "配米饭+汤"},
        });
        createMenuItems(shop6, new String[][]{
                {"草莓蛋糕", "18.00", "新鲜草莓"},
                {"牛角包", "5.00", "法式牛角包"},
                {"提拉米苏", "15.00", "经典意式"},
        });
        createMenuItems(shop7, new String[][]{
                {"菲力牛排", "68.00", "200g澳洲牛肉"},
                {"奶油意面", "28.00", "意式奶油酱"},
                {"披萨", "38.00", "9寸手工披萨"},
        });
        createMenuItems(shop8, new String[][]{
                {"珍珠奶茶", "8.00", "经典奶茶"},
                {"百香果绿茶", "9.00", "鲜果茶"},
                {"杨枝甘露", "12.00", "芒果+西柚"},
        });

        // Create reviews
        LocalDateTime now = LocalDateTime.now();

        createReview(user1, shop1, 5, "超级好吃的麻辣烫！食材新鲜，汤底浓郁，每次必去！", now.minusDays(5));
        createReview(user2, shop1, 4, "味道不错，价格也合理，就是高峰期排队有点久。", now.minusDays(3));
        createReview(user3, shop2, 5, "拉面很正宗，师傅是兰州人，面条劲道！", now.minusDays(4));
        createReview(user1, shop3, 4, "咖啡不错，环境安静，适合自习。", now.minusDays(2));
        createReview(user2, shop3, 5, "抹茶蛋糕超好吃！强烈推荐！", now.minusDays(1));
        createReview(user3, shop4, 5, "烧烤味道绝了，毕业前一定要多吃几次！", now.minusDays(6));
        createReview(user1, shop5, 3, "中规中矩，价格还算实惠。", now.minusDays(7));
        createReview(user2, shop6, 5, "面包都是现烤的，很香！", now.minusDays(2));
        createReview(user3, shop7, 4, "牛排不错，约会好去处。", now.minusDays(5));
        createReview(user1, shop8, 5, "性价比很高的奶茶店，天天喝！", now.minusDays(1));

        // Create favorites
        createFavorite(user1, shop1);
        createFavorite(user1, shop3);
        createFavorite(user2, shop2);
        createFavorite(user3, shop4);
        createFavorite(user3, shop8);

        // Create likes on reviews
        // Reviews are already created above
    }

    private Shop createShop(String name, String location, String category, String description) {
        Shop shop = Shop.builder()
                .name(name)
                .location(location)
                .category(category)
                .description(description)
                .avgRating(0.0)
                .reviewCount(0)
                .build();
        return shopRepository.save(shop);
    }

    private void createMenuItems(Shop shop, String[][] items) {
        int order = 1;
        for (String[] item : items) {
            MenuItem mi = MenuItem.builder()
                    .shop(shop)
                    .name(item[0])
                    .price(new BigDecimal(item[1]))
                    .description(item[2])
                    .sortOrder(order++)
                    .build();
            menuItemRepository.save(mi);
        }
    }

    private Review createReview(User user, Shop shop, int rating, String content, LocalDateTime date) {
        Review review = Review.builder()
                .user(user)
                .shop(shop)
                .rating(rating)
                .content(content)
                .status(Review.Status.APPROVED)
                .likeCount((int) (Math.random() * 20))
                .createdAt(date)
                .build();
        review = reviewRepository.save(review);

        // Update shop rating
        updateShopStats(shop);
        return review;
    }

    private void createFavorite(User user, Shop shop) {
        Favorite fav = Favorite.builder()
                .user(user)
                .shop(shop)
                .build();
        favoriteRepository.save(fav);
    }

    private void updateShopStats(Shop shop) {
        Double avg = reviewRepository.findAverageRatingByShopId(shop.getId());
        long count = reviewRepository.countByShopIdAndStatus(shop.getId(), Review.Status.APPROVED);
        shop.setAvgRating(avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0);
        shop.setReviewCount((int) count);
        shopRepository.save(shop);
    }
}
