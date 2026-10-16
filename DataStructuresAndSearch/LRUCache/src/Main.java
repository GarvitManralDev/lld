void main() {
    LRUCache<String,Integer> cache = new LRUCache<>(5);

    cache.put("Garvit1",1);
    cache.put("Garvit2",2);
    cache.put("Garvit3",3);
    cache.put("Garvit4",4);
    cache.put("Garvit5",5);
    cache.put("Garvit6",6);

    cache.printCache();
    System.out.println();
    System.out.println(cache.get("Garvit3"));
    cache.printCache();
}
