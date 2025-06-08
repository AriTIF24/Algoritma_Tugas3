import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String csvFile = "train.csv";
        List<Rumah> daftarRumah = new ArrayList<>();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);



                if (data.length <= 80) {
                    System.err.println("Melewatkan baris (jumlah kolom tidak cukup): " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(data[0].trim()); // Id


                    int overallQual = parseSafely(data[18].trim()); // OverallQual
                    int grLivArea = parseSafely(data[43].trim()); // GrLivArea
                    int garageCars = parseSafely(data[62].trim()); // GarageCars
                    int totalBsmtSF = parseSafely(data[36].trim()); // TotalBsmtSF
                    int fullBath = parseSafely(data[40].trim()); // FullBath
                    int yearBuilt = parseSafely(data[19].trim()); // YearBuilt
                    int salePrice = parseSafely(data[80].trim()); // SalePrice

                    daftarRumah.add(new Rumah(id, overallQual, grLivArea, garageCars,
                            totalBsmtSF, fullBath, yearBuilt, salePrice));
                } catch (NumberFormatException e) {
                    System.err.println("Melewatkan baris karena kesalahan format angka: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);


        if (!daftarRumah.isEmpty()) {
            System.out.println("---------------------------------------------");
            System.out.println("Data rumah pertama yang dibaca:");
            System.out.println(daftarRumah.get(0));
            System.out.println("Total data rumah yang berhasil dibaca: " + daftarRumah.size());
            System.out.println("---------------------------------------------");
        } else {
            System.out.println("Tidak ada data rumah yang berhasil dibaca.");
            return; // Keluar jika tidak ada data
        }

        // --- Penerapan Algoritma Pengurutan ---
        System.out.println("\nMengurutkan daftar rumah berdasarkan Harga Jual (Ascending) menggunakan Merge Sort...");
        mergeSortRumahBySalePrice(daftarRumah); // Mengurutkan
        System.out.println("Pengurutan selesai.");
        System.out.println("Data rumah setelah diurutkan (5 teratas):");
        for (int i = 0; i < Math.min(5, daftarRumah.size()); i++) {
            System.out.println(daftarRumah.get(i));
        }
        System.out.println("---------------------------------------------");


        // --- Penerapan Algoritma Pencarian ---
        System.out.println("\n--- Pencarian Rumah berdasarkan ID (Binary Search) ---");
        System.out.print("Masukkan ID rumah yang ingin dicari: ");
        int searchId = scanner.nextInt();

        Rumah foundRumah = binarySearchRumahById(daftarRumah, searchId);

        if (foundRumah != null) {
            System.out.println("Rumah ditemukan: " + foundRumah);
        } else {
            System.out.println("Rumah dengan ID " + searchId + " tidak ditemukan.");
        }
        System.out.println("---------------------------------------------");

        scanner.close();
    }



    private static int parseSafely(String s) {
        if (s == null || s.trim().isEmpty() || s.trim().equalsIgnoreCase("NA")) {
            return 0; // Mengembalikan 0 atau nilai default lainnya
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {

            return 0;
        }
    }

    // --- Implementasi Algoritma Pencarian Biner ---
    public static Rumah binarySearchRumahById(List<Rumah> daftarRumah, int targetId) {
        List<Rumah> sortedByIdList = new ArrayList<>(daftarRumah);
        sortedByIdList.sort(Comparator.comparing(Rumah::getId)); // Urutkan berdasarkan ID sebelum pencarian

        int low = 0;
        int high = sortedByIdList.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Rumah midRumah = sortedByIdList.get(mid);

            if (midRumah.getId() == targetId) {
                return midRumah;
            } else if (midRumah.getId() < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // Rumah tidak ditemukan
    }

    // --- Implementasi Algoritma Pengurutan Merge Sort ---
    public static void mergeSortRumahBySalePrice(List<Rumah> daftarRumah) {
        if (daftarRumah.size() < 2) {
            return;
        }

        int mid = daftarRumah.size() / 2;
        List<Rumah> leftHalf = new ArrayList<>(daftarRumah.subList(0, mid));
        List<Rumah> rightHalf = new ArrayList<>(daftarRumah.subList(mid, daftarRumah.size()));

        mergeSortRumahBySalePrice(leftHalf);
        mergeSortRumahBySalePrice(rightHalf);

        merge(daftarRumah, leftHalf, rightHalf);
    }

    private static void merge(List<Rumah> daftarRumah, List<Rumah> left, List<Rumah> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getSalePrice() <= right.get(j).getSalePrice()) {
                daftarRumah.set(k++, left.get(i++));
            } else {
                daftarRumah.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            daftarRumah.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            daftarRumah.set(k++, right.get(j++));
        }
    }
}