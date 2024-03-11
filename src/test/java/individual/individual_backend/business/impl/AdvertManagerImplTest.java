package individual.individual_backend.business.impl;

import individual.individual_backend.business.AdvertManager;
import individual.individual_backend.business.converters.AdvertsConverter;
import individual.individual_backend.domain.Advert;
import individual.individual_backend.domain.Car;
import individual.individual_backend.domain.Role;
import individual.individual_backend.domain.User;
import individual.individual_backend.persistence.AdvertRepository;
import individual.individual_backend.persistence.entity.AdvertEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class AdvertManagerImplTest {
    private AdvertRepository advertRepositoryMock;
    private AdvertManager advertManager;

    @BeforeEach
    void setUp() {
        advertRepositoryMock = mock(AdvertRepository.class);
        advertManager = new AdvertManagerImpl(advertRepositoryMock);
    }

    @Test
    void createAdvert_shouldReturnAUniqueIdWhenAdvertIsCreated() {
        // Arrange
        User user = User.builder()
                .id(1)
                .password("123")
                .email("email.gmail.com")
                .role(Role.CAR_RENTER)
                .age(19)
                .name("Ivan")
                .build();

        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Advert advert = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car)
                .carOwner(user)
                .build();

        AdvertEntity advertEntity = AdvertsConverter.toEntity(advert);
        when(advertRepositoryMock.save(advertEntity)).thenReturn(advertEntity);

        // Act
        Integer actualResult = advertManager.createAdvert(AdvertsConverter.toDomain(advertEntity));
        Integer expectedResult = 1;

        // Assert
        assertEquals(expectedResult, actualResult);
        verify(advertRepositoryMock).save(advertEntity);
    }

    @Test
    void getAdverts_shouldReturnAnEmptyListWhenNoAdvertsArePresent() {
        // Arrange
        when(advertRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Advert> actualAdverts = advertManager.getAllAdverts();

        // Assert
        assertTrue(actualAdverts.isEmpty());
        verify(advertRepositoryMock).findAll();
    }

    @Test
    void getAdverts_shouldReturnAListOfAdvertsWhenAdvertsArePresent() {
        // Arrange
        User user = User.builder()
                .id(1)
                .password("123")
                .email("email@gmail.com")
                .role(Role.CAR_OWNER)
                .age(30)
                .name("John")
                .build();

        Car car1 = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Car car2 = Car.builder()
                .id(2)
                .brand("Audi")
                .model("A4")
                .yearOfConstruction(2022)
                .colour("White")
                .kilometers(15000)
                .build();

        Advert advert1 = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car1)
                .carOwner(user)
                .build();

        Advert advert2 = Advert.builder()
                .id(2)
                .availableFrom(LocalDate.of(2023, 11, 5))
                .maximumAvailableDays(5)
                .pricePerDay(40.0)
                .car(car2)
                .carOwner(user)
                .build();

        List<AdvertEntity> advertEntities = Arrays.asList(
                AdvertsConverter.toEntity(advert1),
                AdvertsConverter.toEntity(advert2)
        );

        when(advertRepositoryMock.findAll()).thenReturn(advertEntities);

        // Act
        List<Advert> actualAdverts = advertManager.getAllAdverts();

        // Assert
        assertEquals(2, actualAdverts.size());
        verify(advertRepositoryMock).findAll();
    }

    @Test
    void deleteAdvert_shouldRemoveTheAdvertWithTheGivenIDFromTheRepository() {
        // Arrange
        int advertIdToDelete = 1;
        doNothing().when(advertRepositoryMock).deleteById(advertIdToDelete);

        // Act
        advertManager.deleteAdvert(advertIdToDelete);

        // Assert
        verify(advertRepositoryMock).deleteById(advertIdToDelete);
    }

    @Test
    void updateAdvert_shouldUpdateAnExistingAdvertsDetailsIfItExistsInTheRepository() {
        // Arrange
        User user = User.builder()
                .id(1)
                .password("123")
                .email("email@gmail.com")
                .role(Role.CAR_OWNER)
                .age(30)
                .name("John")
                .build();

        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Advert advertToUpdate = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car)
                .carOwner(user)
                .build();

        Advert updatedAdvert = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 12, 1))
                .maximumAvailableDays(5)
                .pricePerDay(60.0)
                .car(car)
                .carOwner(user)
                .build();

        AdvertEntity advertEntityToUpdate = AdvertsConverter.toEntity(advertToUpdate);
        Optional<AdvertEntity> optionalAdvertEntity = Optional.of(advertEntityToUpdate);
        when(advertRepositoryMock.findById(advertToUpdate.getId())).thenReturn(optionalAdvertEntity);

        // Act
        advertManager.updateAdvert(updatedAdvert);

        // Assert
        verify(advertRepositoryMock).save(AdvertsConverter.toEntity(updatedAdvert));
    }

    @Test
    void getAdvert_shouldReturnAdvertWhenItExists() {
        // Arrange
        User user = User.builder()
                .id(1)
                .password("123")
                .email("email@gmail.com")
                .role(Role.CAR_OWNER)
                .age(30)
                .name("John")
                .build();

        Car car1 = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Advert advert1 = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car1)
                .carOwner(user)
                .build();

        AdvertEntity advertEntity = AdvertsConverter.toEntity(advert1);

        when(advertRepositoryMock.findById(advert1.getId())).thenReturn(Optional.of(advertEntity));

        // Act
        Advert actualAdvert = advertManager.getAdvert(advert1.getId());

        // Assert
        assertEquals(AdvertsConverter.toDomain(advertEntity), actualAdvert);
        verify(advertRepositoryMock).findById(advert1.getId());
    }

    @Test
    void getAdvert_shouldReturnNullWhenAdvertDoesNotExist() {
        // Arrange
        Integer advertId = 1;

        when(advertRepositoryMock.findById(advertId)).thenReturn(Optional.empty());

        // Act
        Advert actualAdvert = advertManager.getAdvert(advertId);

        // Assert
        assertNull(actualAdvert);
        verify(advertRepositoryMock).findById(advertId);
    }

    @Test
    void findByCarBrand_shouldReturnListOfAdverts() {
        // Arrange
        String brand = "BMW";
        User user = User.builder()
                .id(1)
                .password("123")
                .email("email@gmail.com")
                .role(Role.CAR_OWNER)
                .age(30)
                .name("John")
                .build();

        Car car1 = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Car car2 = Car.builder()
                .id(2)
                .brand("BMW")
                .model("A4")
                .yearOfConstruction(2022)
                .colour("White")
                .kilometers(15000)
                .build();

        Advert advert1 = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car1)
                .carOwner(user)
                .build();

        Advert advert2 = Advert.builder()
                .id(2)
                .availableFrom(LocalDate.of(2023, 11, 5))
                .maximumAvailableDays(5)
                .pricePerDay(40.0)
                .car(car2)
                .carOwner(user)
                .build();

        List<AdvertEntity> advertEntities = Arrays.asList(
                AdvertsConverter.toEntity(advert1),
                AdvertsConverter.toEntity(advert2)
        );

        when(advertRepositoryMock.findByCar_Brand(brand)).thenReturn(advertEntities);

        // Act
        List<Advert> actualAdverts = advertManager.findByCarBrand(brand);

        // Assert
        assertEquals(advertEntities.size(), actualAdverts.size());
        verify(advertRepositoryMock).findByCar_Brand(brand);
    }

    @Test
    void findByUserId_shouldReturnListOfAdvertsWhenUserIdNotZero() {
        // Arrange
        Integer userId = 1;
        User user = User.builder()
                .id(1)
                .password("123")
                .email("email@gmail.com")
                .role(Role.CAR_OWNER)
                .age(30)
                .name("John")
                .build();

        Car car1 = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Car car2 = Car.builder()
                .id(2)
                .brand("BMW")
                .model("A4")
                .yearOfConstruction(2022)
                .colour("White")
                .kilometers(15000)
                .build();

        Advert advert1 = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car1)
                .carOwner(user)
                .build();

        Advert advert2 = Advert.builder()
                .id(2)
                .availableFrom(LocalDate.of(2023, 11, 5))
                .maximumAvailableDays(5)
                .pricePerDay(40.0)
                .car(car2)
                .carOwner(user)
                .build();

        List<AdvertEntity> advertEntities = Arrays.asList(
                AdvertsConverter.toEntity(advert1),
                AdvertsConverter.toEntity(advert2)
        );

        when(advertRepositoryMock.findByUserId(userId)).thenReturn(advertEntities);

        // Act
        List<Advert> actualAdverts = advertManager.findByUserId(userId);

        // Assert
        assertEquals(advertEntities.size(), actualAdverts.size());
        verify(advertRepositoryMock).findByUserId(userId);
    }

    @Test
    void findByUserId_shouldReturnNullWhenUserIdIsZero() {
        // Arrange
        Integer userId = 0;

        // Act
        List<Advert> actualAdverts = advertManager.findByUserId(userId);

        // Assert
        assertTrue(actualAdverts.isEmpty());
        verify(advertRepositoryMock, never()).findByUserId(userId);
    }

    @Test
    void saveCarPicture_shouldUpdateAdvertPictureWhenAdvertExists() {
        // Arrange
        Integer advertId = 1;
        String carPicture = "new_picture.jpg";

        User user = User.builder()
                .id(1)
                .password("123")
                .email("email@gmail.com")
                .role(Role.CAR_OWNER)
                .age(30)
                .name("John")
                .build();

        Car car1 = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .yearOfConstruction(2020)
                .colour("Black")
                .kilometers(30000)
                .build();

        Advert advert1 = Advert.builder()
                .id(1)
                .availableFrom(LocalDate.of(2023, 11, 1))
                .maximumAvailableDays(7)
                .pricePerDay(50.0)
                .car(car1)
                .carOwner(user)
                .build();

        AdvertEntity advertEntity = AdvertsConverter.toEntity(advert1);

        when(advertRepositoryMock.findById(advertId)).thenReturn(Optional.of(advertEntity));

        // Act
        advertManager.saveCarPicture(advertId, carPicture);

        // Assert
        assertEquals(carPicture, advertEntity.getPicture());
        verify(advertRepositoryMock).saveAndFlush(advertEntity);
    }

    @Test
    void saveCarPicture_shouldNotUpdateAdvertPictureWhenAdvertDoesNotExist() {
        // Arrange
        Integer advertId = 1;
        String carPicture = "new_picture.jpg";

        when(advertRepositoryMock.findById(advertId)).thenReturn(Optional.empty());

        // Act
        advertManager.saveCarPicture(advertId, carPicture);

        // Assert
        verify(advertRepositoryMock, never()).saveAndFlush(any());
    }

    @Test
    void getAveragePriceByBrand_shouldReturnNullWhenNoAdvertsArePresent() {
        // Arrange
        String brand = "NonExistentBrand";
        when(advertRepositoryMock.findByCar_Brand(brand)).thenReturn(new ArrayList<>());

        // Act
        Double averagePrice = advertManager.getAveragePriceByBrand(brand);

        // Assert
        assertNull(averagePrice);
        verify(advertRepositoryMock).findByCar_Brand(brand);
    }

    @Test
    void getAveragePriceByBrand_shouldReturnCorrectAveragePriceWhenAdvertsArePresent() {
        // Arrange
        String brand = "BMW";
        Advert advert1 = Advert.builder().pricePerDay(50.0).build();
        Advert advert2 = Advert.builder().pricePerDay(40.0).build();
        Advert advert3 = Advert.builder().pricePerDay(60.0).build();

        List<AdvertEntity> advertEntities = Arrays.asList(
                AdvertsConverter.toEntity(advert1),
                AdvertsConverter.toEntity(advert2),
                AdvertsConverter.toEntity(advert3)
        );

        when(advertRepositoryMock.findByCar_Brand(brand)).thenReturn(advertEntities);

        // Act
        Double averagePrice = advertManager.getAveragePriceByBrand(brand);

        // Assert
        assertEquals(50.0, averagePrice, 0.001); // Specify a delta to account for potential floating-point errors
        verify(advertRepositoryMock).findByCar_Brand(brand);
    }
}
