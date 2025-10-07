# Reporting API

Spring Boot ve PostgreSQL kullanılarak geliştirilmiş, JWT tabanlı kimlik doğrulama sistemi içeren bir Raporlama API'sidir.

## 📋 İçindekiler

- [Gereksinimler](#gereksinimler)
- [Port Bilgileri](#port-bilgileri)
- [Kurulum ve Çalıştırma](#kurulum-ve-çalıştırma)
- [API Dokümantasyonu](#api-dokümantasyonu)
- [Teknolojiler](#teknolojiler)
- [Proje Yapısı](#proje-yapısı)

## 🔧 Gereksinimler

Projeyi çalıştırmadan önce aşağıdaki gereksinimlerin karşılandığından emin olun:

- **Docker** ve **Docker Compose** yüklü olmalıdır
- **Port 5432** (PostgreSQL için) boş olmalıdır
- **Port 8080** (Spring Boot uygulaması için) boş olmalıdır

> ⚠️ **ÖNEMLİ:** Eğer 5432 veya 8080 portları kullanımdaysa, docker-compose.yml dosyasından port ayarlarını değiştirebilir veya bu portları kullanan servisleri durdurabilirsiniz.

### Port Kontrolü

**Windows PowerShell için:**
```powershell
# 5432 portunu kontrol et
netstat -ano | findstr :5432

# 8080 portunu kontrol et
netstat -ano | findstr :8080
```

Eğer portlar kullanımdaysa, PID'yi bulup servisi durdurabilirsiniz:
```powershell
# PID'ye göre servisi sonlandır
taskkill /PID <PID_NUMARASI> /F
```

## 🚀 Port Bilgileri

Proje aşağıdaki portları kullanmaktadır:

| Servis | Port | Açıklama |
|--------|------|----------|
| **PostgreSQL Database** | `5432` | Veritabanı servisi |
| **Spring Boot API** | `8080` | REST API servisi |

## 📦 Kurulum ve Çalıştırma

### Projeyi Ayağa Kaldırma

Projeyi çalıştırmak için proje ana dizininde aşağıdaki komutu çalıştırın:

```bash
docker-compose up
```

Bu komut:
1. PostgreSQL veritabanını başlatır (port 5432)
2. Veritabanının hazır olmasını bekler (health check)
3. Spring Boot uygulamasını build eder ve başlatır (port 8080)
4. Veritabanı tablolarını oluşturur ve örnek verileri yükler (data.sql)

### Arka Planda Çalıştırma

Servisleri arka planda çalıştırmak için:

```bash
docker-compose up -d
```

### Logları Görüntüleme

```bash
# Tüm servislerin logları
docker-compose logs -f

# Sadece API logları
docker-compose logs -f reportingapi

# Sadece Database logları
docker-compose logs -f postgresqldb
```

### Servisleri Durdurma

```bash
docker-compose down
```

### Servisleri ve Verileri Tamamen Silme

```bash
docker-compose down -v
```

> ⚠️ **DİKKAT:** `-v` parametresi ile tüm volume'ler (veritabanı verileri dahil) silinir!

## 📚 API Dokümantasyonu

Proje çalıştıktan sonra, Swagger UI dokümantasyonuna aşağıdaki adresten erişebilirsiniz:

```
http://localhost:8080/swagger-ui.html
```

### Ana Endpointler

#### Kimlik Doğrulama
- `POST /api/v1/auth/register` - Yeni kullanıcı kaydı
- `POST /api/v1/auth/login` - Kullanıcı girişi (JWT token alır)

#### Transaction İşlemleri (JWT token gerektirir)
- `POST /api/v1/transactions/report` - Transaction raporu oluşturma
- `POST /api/v1/transactions/list` - Transaction listesi sorgulama
- `POST /api/v1/transactions` - Belirli bir transaction detayını getirme
- `POST /api/v1/client` - Müşteri bilgilerini getirme

### Örnek Kullanım

1. **Kullanıcı Girişi:**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

2. **Token ile API Çağrısı:**
```bash
curl -X POST http://localhost:8080/api/v1/transactions/report \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "fromDate": "2024-01-01",
    "toDate": "2024-12-31"
  }'
```

## 🛠 Teknolojiler

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Security** - JWT tabanlı kimlik doğrulama
- **Spring Data JPA** - Veritabanı işlemleri
- **PostgreSQL 15** - İlişkisel veritabanı
- **Docker & Docker Compose** - Konteynerizasyon
- **Lombok** - Boilerplate kod azaltma
- **SpringDoc OpenAPI (Swagger)** - API dokümantasyonu
- **Maven** - Proje yönetimi ve bağımlılık yönetimi

## 📁 Proje Yapısı

```
ReportingAPI/
├── src/
│   └── main/
│       ├── java/com/msansar/ReportingAPI/
│       │   ├── config/              # Güvenlik ve JWT yapılandırmaları
│       │   ├── controller/          # REST API endpoint'leri
│       │   ├── dto/                 # Data Transfer Objects
│       │   ├── enums/               # Enum tanımlamaları
│       │   ├── exception/           # Exception handler'lar
│       │   ├── filter/              # JWT authentication filter
│       │   ├── model/               # JPA Entity'ler
│       │   ├── repository/          # Database repository'ler
│       │   ├── service/             # Business logic
│       │   └── util/                # Yardımcı sınıflar
│       └── resources/
│           ├── application.properties  # Uygulama ayarları
│           └── data.sql                # Başlangıç verileri
├── docker-compose.yml              # Docker servisleri
├── Dockerfile                      # Spring Boot image
└── pom.xml                         # Maven bağımlılıkları
```

## 🔒 Güvenlik

Proje JWT (JSON Web Token) tabanlı kimlik doğrulama kullanmaktadır. Korunan endpoint'lere erişmek için:

1. `/api/v1/auth/login` endpoint'i ile giriş yapın
2. Dönen JWT token'ı alın
3. Diğer isteklerde `Authorization: Bearer <token>` header'ını ekleyin

Token geçerlilik süresi: **10 dakika** (600000 ms)

## 🐛 Sorun Giderme

### Port zaten kullanılıyor hatası

```
Error starting userland proxy: listen tcp4 0.0.0.0:5432: bind: address already in use
```

**Çözüm:** İlgili portu kullanan servisi durdurun veya `docker-compose.yml` dosyasındaki port ayarlarını değiştirin.

### Database connection hatası

Eğer uygulama veritabanına bağlanamıyorsa:

1. PostgreSQL container'ının çalıştığından emin olun:
```bash
docker-compose ps
```

2. Database loglarını kontrol edin:
```bash
docker-compose logs postgresqldb
```

### Container'ları yeniden başlatma

```bash
# Servisleri durdur
docker-compose down

# Image'leri yeniden build et ve başlat
docker-compose up --build
```

## 📝 Lisans

Bu proje demo amaçlıdır.

## 👤 Geliştirici

**Mustafa Sansar**

---

**Son Güncelleme:** Ekim 2025

