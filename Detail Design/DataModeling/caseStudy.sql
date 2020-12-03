CREATE TABLE `Book`  (
  `id` int NOT NULL,
  `author` varchar(45) NULL,
  `coverType` varchar(45) NULL,
  `publisher` varchar(45) NULL,
  `publishDate` datetime NULL,
  `numOfPage` int NULL,
  `language` varchar(45) NULL,
  `bookCategory` varchar(45) NULL
);

CREATE TABLE `Card`  (
  `id` int NOT NULL,
  `cardCode` varchar(15) NULL,
  `owner` varchar(45) NULL,
  `cvvCode` varchar(3) NULL,
  `dateExpired` varchar(4) NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `CD`  (
  `id` int NULL,
  `artist` varchar(45) NULL,
  `recordLabel` varchar(45) NULL,
  `musicType` varchar(200) NULL,
  `releaseDate` varchar(100) NULL
);

CREATE TABLE `DeliveryInfo`  (
  `id` int NOT NULL,
  `name` varchar(45) NULL,
  `province` varchar(45) NULL,
  `instructions` varchar(200) NULL,
  `address` varchar(100) NULL,
  PRIMARY KEY (`id`)

);

CREATE TABLE `DVD`  (
  `id` int NOT NULL,
  `discType` varchar(45) NOT NULL,
  `director` varchar(45) NOT NULL,
  `runtime` int NOT NULL,
  `studio` varchar(45) NOT NULL,
  `subtitle` varchar(45) NOT NULL,
  `releaseDate` datetime NOT NULL
);

CREATE TABLE `Invoice`  (
  `id` int NOT NULL,
  `totalAmount` int NULL,
  `orderId` int NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Media`  (
  `id` int NOT NULL,
  `category` varchar(45) NULL,
  `price` int NULL,
  `quantity` int NULL,
  `title` varchar(45) NULL,
  `value` int NULL,
  `imageUrl` varchar(45) NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Order`  (
  `id` int NOT NULL,
  `shippingFees` varchar(45) NULL,
  `deliveryInfoId` int NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `OrderMedia`  (
  `orderId` int NOT NULL,
  `price` int NULL,
  `quantity` varchar(11) NULL,
  `mediaId` int NULL
);

CREATE TABLE `PaymentTracsaction`  (
  `id` int NOT NULL,
  `createAt` datetime NULL,
  `content` varchar(45) NULL,
  `method` varchar(200) NULL,
  `cardId` int NULL,
  `invoiceId` int NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `Book` ADD CONSTRAINT `fk_Book_Media_1` FOREIGN KEY (`id`) REFERENCES `Media` (`id`);
ALTER TABLE `Card` ADD CONSTRAINT `fk_Card_PaymentTracsaction_1` FOREIGN KEY (`id`) REFERENCES `PaymentTracsaction` (`cardId`);
ALTER TABLE `CD` ADD CONSTRAINT `fk_CD_Media_1` FOREIGN KEY (`id`) REFERENCES `Media` (`id`);
ALTER TABLE `DeliveryInfo` ADD CONSTRAINT `fk_DeliveryInfo_Order_1` FOREIGN KEY (`id`) REFERENCES `Order` (`deliveryInfoId`);
ALTER TABLE `DVD` ADD CONSTRAINT `fk_DVD_Media_1` FOREIGN KEY (`id`) REFERENCES `Media` (`id`);
ALTER TABLE `Invoice` ADD CONSTRAINT `fk_Invoice_PaymentTracsaction_1` FOREIGN KEY (`id`) REFERENCES `PaymentTracsaction` (`invoiceId`);
ALTER TABLE `Media` ADD CONSTRAINT `fk_Media_OrderMedia_1` FOREIGN KEY (`id`) REFERENCES `OrderMedia` (`mediaId`);
ALTER TABLE `Order` ADD CONSTRAINT `fk_Order_Invoice_1` FOREIGN KEY (`id`) REFERENCES `Invoice` (`orderId`);
ALTER TABLE `Order` ADD CONSTRAINT `fk_Order_OrderMedia_1` FOREIGN KEY (`id`) REFERENCES `OrderMedia` (`orderId`);

