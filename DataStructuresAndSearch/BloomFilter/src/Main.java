import models.BloomFilter;

// Bloom filter demo: a URL blacklist sized for 1,000 URLs with a 1% false positive rate
void main() {
    BloomFilter blacklist = new BloomFilter(1000, 0.01);

    blacklist.add("https://malware.com");
    blacklist.add("https://phishing.net");
    blacklist.add("https://spam.org");

    // Added URLs always return true (no false negatives)
    System.out.println("malware.com  -> " + blacklist.mightContain("https://malware.com"));    // true
    System.out.println("phishing.net -> " + blacklist.mightContain("https://phishing.net"));   // true

    // URLs never added almost always return false ("definitely absent")
    System.out.println("google.com   -> " + blacklist.mightContain("https://google.com"));     // false
    System.out.println("github.com   -> " + blacklist.mightContain("https://github.com"));     // false

    // After clear, nothing is in the filter
    blacklist.clear();
    System.out.println("after clear, malware.com -> " + blacklist.mightContain("https://malware.com"));  // false
}
