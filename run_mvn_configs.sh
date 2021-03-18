#1 on Prem - Run a single test on a single browser instance on your own machine
mvn test -Pon-premise -Dtest=OrderByFilterTest
mvn test -Pon-premise -Dtest=ApplyAppleAndSamsungFilterTest
mvn test -Pon-premise -Dtest=FavouritesRedirectsLoginTest
mvn test -Pon-premise -Dtest=LockedAccountTest
mvn test -Pon-premise -Dtest=ImageLoadingTest
mvn test -Pon-premise -Dtest=OrderCountTest
mvn test -Pon-premise -Dtest=FavouriteCountTest
mvn test -Pon-premise -Dtest=EndToEndFlowTest
mvn test -Pon-premise -Dtest=OffersTest

#2 on-prem-suite
mvn test -Pon-premise

#3 docker
mvn test -Pdocker -Dtest=OrderByFilterTest
mvn test -Pdocker -Dtest=ApplyAppleAndSamsungFilterTest
mvn test -Pdocker -Dtest=FavouritesRedirectsLoginTest
mvn test -Pdocker -Dtest=LockedAccountTest
mvn test -Pdocker -Dtest=ImageLoadingTest
mvn test -Pdocker -Dtest=OrderCountTest
mvn test -Pdocker -Dtest=FavouriteCountTest
mvn test -Pdocker -Dtest=EndToEndFlowTest
mvn test -Pdocker -Dtest=OffersTest


#4 docker-parallel
mvn test -Pdocker

#5 bstack-single
mvn test -Psingle -Dtest=OrderByFilterTest
mvn test -Psingle -Dtest=ApplyAppleAndSamsungFilterTest
mvn test -Psingle -Dtest=FavouritesRedirectsLoginTest
mvn test -Psingle -Dtest=LockedAccountTest
mvn test -Psingle -Dtest=ImageLoadingTest
mvn test -Psingle -Dtest=OrderCountTest
mvn test -Psingle -Dtest=FavouriteCountTest
mvn test -Psingle -Dtest=EndToEndFlowTest  -->Failed
mvn test -Psingle -Dtest=OffersTest

#6 bstack-local
mvn test -Plocal -Dtest=OrderByFilterTest
mvn test -Plocal -Dtest=ApplyAppleAndSamsungFilterTest
mvn test -Plocal -Dtest=FavouritesRedirectsLoginTest
mvn test -Plocal -Dtest=LockedAccountTest
mvn test -Plocal -Dtest=ImageLoadingTest
mvn test -Plocal -Dtest=OrderCountTest
mvn test -Plocal -Dtest=FavouriteCountTest
mvn test -Plocal -Dtest=EndToEndFlowTest --> Failed
mvn test -Plocal -Dtest=OffersTest

#7 bstack-local-parallel
mvn test -Plocal

#bstack-local-parallel-browsers
mvn test -Plocal-parallel