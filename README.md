# Advance Programming Tutorial

<details>
<summary>Modul 1</summary>

## Reflection 1

### 🔹 Clean Code and Secure Coding 

- Menggunakan nama yang jelas dan deskriptif untuk variabel, fungsi, dan kelas.  
- Memakai fungsi yang pendek, melakukan satu tugas, dan memiliki nama yang mencerminkan tujuannya.
- Tidak menulis komen yang boros.
- Memerhatikan layout kode sehingga mudah dibaca.
- Menggunakan post method.

### 🔹 Improvement

Salah satu masalah yang saya alami adalah tidak bisa menggunakan PutMapping dan DeleteMapping untuk fitur edit dan delete sehingga saya tetap menggunakan PostMapping untuk keduanya.


## Reflection 2

1. Dengan menulis unit test, saya merasa lebih yakin terhadap kebenaran kode. Jumlah unit test dalam sebuah class 
   tidak memiliki batasan, bisa disesuaikan dengan kompleksitas program. Meskipun memiliki 100% code coverage, kode 
   belum pasti bebas dari bug atau error. Ada metode pengujian lainnya yang dapat dilakukan.
2. Menggunakan setup procedures dan instance variables yang sama, dapat mempertahankan kekonsistenan dan memudahkan
   maintenance. Namun terdapat potensi dalam isu clean code, seperti duplikasi dan penggunaan nama yang abstrak. 
   Setup procedures sebaiknya disesuaikan dengan test yang akan dilakukan begitupun dengan variabel.

</details>

<details>
<summary>Modul 2</summary>

## Reflection

### Code Quality Issues

1. **Remove unnecessary modifier**
   
   Sebelumnya pada ProductService saya menuliskan modifier `public` untuk setiap method. Namun karena itu merupakan 
   Interface, sudah otomatis tiap method adalah `public abstract` tanpa perlu ditulis.


2. **Change import**

   Sebelum perbaikan, saya menggunakan `*` saat mengimpor

   ```
   import org.springframework.web.bind.annotation.*;
   ```

   Karena notasi yang diperlukan hanya sedikit, saya ubah menjadi satu-satu
   ```
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PostMapping;
   import org.springframework.web.bind.annotation.ModelAttribute;
   import org.springframework.web.bind.annotation.PathVariable;
   ```

### CI/CD workflows

Menurut saya, implementasi _workflows_ pada program saya sudah memenuhi definisi CI/CD. CI/CD memungkinkan terjadinya 
_testing_ program dan _deployment_ secara otomatis. Dengan menggunakan _github workflows_, program saya bisa 
menjalani _testing_ setiap kali terjadi push di suatu branch. Proses testing dalam _workflow_ CI ini melibatkan 
langkah-langkah seperti checkout code, setup Java toolchain, dan eksekusi unit tests menggunakan Gradle wrapper. 
Selain itu PMD dalam workflow lain menjalankan pengujian keamanan dan analisis kode yang lebih mendalam. Kode yang 
sudah benar kemudian akan di merge ke branch main dan otomatis deploy ke Paas Koyeb.

</details>
<details>
<summary>Modul 3</summary>

## Reflection

### SOLID Principles

1. Single Responsibility Principle - __SRP__

   Setiap kelas memiliki satu tanggung jawab. Sebagai contoh, ProductController dan CarController masing-masing 
   menangani HTTP request yang berbeda untuk entitas masing-masing.

2. Open/Closed Principle - __OCP__
   
   Kelas ProductServiceImpl dan CarServiceImpl dirancang untuk dapat diperluas tetapi tertutup untuk modifikasi. 
   Fungsionalitasnya dapat diperluas dengan menambahkan metode atau kelas baru, tetapi yang sudah ada tidak perlu 
   dimodifikasi.

3. Dependency Inversion Principle - __DIP__

   Suatu kelas harus bergantung pada abstraksi atau antarmuka, bukan implementasi konkret. Car Service dan Product 
   Service bergantung pada suatu interface dan tidak bergantung pada implementasi konkret dari repository.

### Advantages

- Kode lebih terstruktur dan mudah dipahami
- Penambahan fungsi lebih mudah dilakukan tanpa perlu mengubah fungsi yang sudah ada
- Mengurangi potensi masalah pada kode
- Memudahkan testing

### Disadvantages

- Memerlukan usaha yang lebih kompleks pada desain awal
- Terdapat potensi deesain menjadi terlalu kompleks, yang menyebabkan pemborosan waktu dan sumber daya

</details>