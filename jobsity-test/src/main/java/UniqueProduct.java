public class UniqueProduct {
    public static String firstUniqueProduct(String[] products) {

        java.util.Set<String> productsSet = new java.util.LinkedHashSet<>();
        java.util.Set<String> uniqueProducts = new java.util.LinkedHashSet<>();

        for (String product : products) {
            boolean added = productsSet.add(product);

            if (added) {
                uniqueProducts.add(product);
            } else {
                uniqueProducts.remove(product);
            }
        }

        if (uniqueProducts.isEmpty()) {
            return null;
        } else {
            return uniqueProducts.stream().findFirst().get();
        }
    }

    public static void main(String[] args) {
        System.out.println(firstUniqueProduct(new String[] { "Apple", "Computer", "Apple", "Bag"}));
    }
}