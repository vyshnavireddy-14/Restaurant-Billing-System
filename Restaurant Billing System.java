import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    int id;
    String name;
    double price;

    MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return id + ". " + name + " - ₹" + price;
    }
}

class OrderItem {
    MenuItem item;
    int quantity;

    OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    double getTotalPrice() {
        return item.price * quantity;
    }
}

class Restaurant {
    private ArrayList<MenuItem> menu = new ArrayList<>();
    private ArrayList<OrderItem> order = new ArrayList<>();
    private static final double GST_RATE = 0.05; 

    void addMenuItem(int id, String name, double price) {
        for (MenuItem m : menu) {
            if (m.id == id) {
                System.out.println("Item ID already exists.");
                return;
            }
        }
        menu.add(new MenuItem(id, name, price));
        System.out.println("Menu item added.");
    }

    void removeMenuItem(int id) {
        for (MenuItem m : menu) {
            if (m.id == id) {
                menu.remove(m);
                System.out.println("Menu item removed.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    void showMenu() {
        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }
        for (MenuItem m : menu) {
            System.out.println(m);
        }
    }

    MenuItem findMenuItem(int id) {
        for (MenuItem m : menu) {
            if (m.id == id)
                return m;
        }
        return null;
    }

    void addOrderItem(int itemId, int quantity) {
        MenuItem item = findMenuItem(itemId);
        if (item == null) {
            System.out.println("Invalid item ID.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }
        order.add(new OrderItem(item, quantity));
        System.out.println("Item added to order.");
    }

    void generateBill() {
        if (order.isEmpty()) {
            System.out.println("No items ordered.");
            return;
        }

        double subtotal = 0;

        System.out.println("========== RECEIPT ==========");
        for (OrderItem oi : order) {
            double price = oi.getTotalPrice();
            subtotal += price;
            System.out.println(
                oi.item.name + " x " + oi.quantity + " = ₹" + price
            );
        }

        double gst = subtotal * GST_RATE;
        double total = subtotal + gst;

        System.out.println("Subtotal: ₹" + subtotal);
        System.out.println("GST (5%): ₹" + gst);
        System.out.println("Total Bill: ₹" + total);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Restaurant restaurant = new Restaurant();

        // Default menu
        restaurant.addMenuItem(1, "Burger", 120);
        restaurant.addMenuItem(2, "Pizza", 250);
        restaurant.addMenuItem(3, "Pasta", 180);

        while (true) {
            System.out.println(" Restaurant Billing System");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. Order Item");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    restaurant.showMenu();
                    break;

                case "2":
                    try {
                        System.out.print("Enter Item ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Item Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Price: ");
                        double price = Double.parseDouble(sc.nextLine());

                        if (name.isEmpty() || price <= 0) {
                            System.out.println("Invalid input.");
                            break;
                        }

                        restaurant.addMenuItem(id, name, price);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format.");
                    }
                    break;

                case "3":
                    try {
                        System.out.print("Enter Item ID to remove: ");
                        int id = Integer.parseInt(sc.nextLine());
                        restaurant.removeMenuItem(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case "4":
                    try {
                        System.out.print("Enter Item ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Quantity: ");
                        int qty = Integer.parseInt(sc.nextLine());

                        restaurant.addOrderItem(id, qty);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                    break;

                case "5":
                    restaurant.generateBill();
                    break;

                case "6":
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Choose 1–6.");
            }
        }
    }
}