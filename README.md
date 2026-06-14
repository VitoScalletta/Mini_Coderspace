# Mini Coderspace API 🚀

Mini Coderspace, yazılım dünyasındaki yetenekleri (Adaylar) ve işverenleri (Şirketler) güvenli bir platformda buluşturan, rol tabanlı (Role-Based) mikro-ticaret ve ilan yönetim sistemidir. 

Bu proje, modern backend mimarileri göz önünde bulundurularak Spring Boot kullanılarak geliştirilmiş olup, kimlik doğrulama süreçleri JWT (JSON Web Token) ile güvence altına alınmıştır.

## 🛠️ Kullanılan Teknolojiler (Tech Stack)

* **Backend:** Java 17, Spring Boot (3.x)
* **Güvenlik:** Spring Security, JWT (JJWT Impl 0.11.5)
* **Veri Erişimi:** Spring Data JPA, Hibernate
* **Veritabanı & Altyapı:** PostgreSQL, Docker (docker-compose desteği)
* **Proje Yönetimi:** Maven

## 🔒 Mimari ve Güvenlik Özellikleri

* **JWT Mimarisi (Stateless Authentication):** Kullanıcı girişlerinde oturum (session) tutulmaz, her istekte doğrulanan 30 günlük yetki token'ları (Bearer Token) kullanılır.
* **Rol Bazlı Erişim Kontrolü (RBAC):** Sistemde `CANDIDATE` (Aday) ve `COMPANY` (Şirket) olmak üzere iki farklı rol bulunur. İlan açma veya başvuru onaylama gibi işlemler `@PreAuthorize` anotasyonları ile sadece ilgili rollere kısıtlanmıştır.
* **Kimlik Hırsızlığı Koruması:** İsteklerin Body'sinden ID almak yerine, işlemi yapan kullanıcının kimliği doğrudan `SecurityContextHolder` üzerinden okunarak veri manipülasyonu engellenmiştir.
* **Gelişmiş Filtreleme (JPA):** İlanlar arasında kelime (keyword) ve statü (ACTIVE/CLOSED) bazlı arama yapabilmek için özel `@Query` ve Method Naming yetenekleri entegre edilmiştir.

## 📌 API Uç Noktaları (Endpoints)

Aşağıdaki tablo, sistemdeki mevcut API uç noktalarını ve bu noktalara erişim yetkilerini (Rol) göstermektedir.

| HTTP Metodu | Endpoint | Açıklama | Yetki (Rol) |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Yeni kullanıcı (Aday/Şirket) kaydı. | Herkes (Permit All) |
| `POST` | `/api/auth/login` | Sisteme giriş ve JWT Token üretimi. | Herkes (Permit All) |
| `GET` | `/api/jobs` | İlanları listeleme (Keyword ve Statü filtreli). | Sistemdeki Herkes |
| `POST` | `/api/jobs` | Yeni bir iş ilanı oluşturma. | `COMPANY` |
| `PUT` | `/api/jobs/{id}` | Mevcut ilanı güncelleme (Sadece ilan sahibi). | `COMPANY` |
| `DELETE` | `/api/jobs/{id}` | İlanı silme (Sadece ilan sahibi). | `COMPANY` |
| `POST` | `/api/application` | Bir ilana başvuru yapma. | `CANDIDATE` |
| `PUT` | `/api/application/{id}` | Başvuru durumunu (ACCEPTED/REJECTED) güncelleme. | `COMPANY` |

## 🚀 Kurulum ve Çalıştırma

Projeyi yerel ortamınızda (Localhost) ayağa kaldırmak için aşağıdaki adımları izleyebilirsiniz:

1. **Projeyi Klonlayın:**

    git clone [https://github.com/vitoscalletta/mini_coderspace.git](https://github.com/vitoscalletta/mini_coderspace.git)
    cd mini_coderspace

2. **Docker Altyapısını Başlatın:**
    Proje dizininde bulunan docker-compose.yml dosyasını kullanarak PostgreSQL veritabanını ayağa kaldırın.

    docker-compose up -d

3. **Uygulamayı Çalıştırın:**
    Maven Wrapper kullanarak uygulamayı derleyip başlatın.

    ./mvnw spring-boot:run

*Uygulama varsayılan olarak http://localhost:8080 portunda çalışacaktır.*

## 👨‍💻 Geliştirici

**Muhammet Emre Kain**
