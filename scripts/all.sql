


-- 导出  表 sva.store 结构
DROP TABLE IF EXISTS `store`;
CREATE TABLE IF NOT EXISTS `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.maps 结构

DROP TABLE IF EXISTS `maps`;
CREATE TABLE `maps` (
    `floor` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
    `xo` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
    `yo` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
    `scale` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
    `coordinate` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
    `angle` FLOAT NOT NULL DEFAULT '0',
    `path` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
    `placeId` INT(11) NULL DEFAULT NULL,
    `mapId` BIGINT(20) NOT NULL,
    `floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
    `imgWidth` INT(11) NULL DEFAULT NULL,
    `imgHeight` INT(11) NULL DEFAULT NULL,
    `floorid` DECIMAL(10,2) NULL DEFAULT NULL,
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `svg` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `route` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `pathfile` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `updateTime` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `zAnMap` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `zIosMap` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `zAnMapPath` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `zIosMapPath` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `mapType` INT(11) NOT NULL DEFAULT '0' COMMENT '0:为商场，1：停车场',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `floorNo` (`floorNo`),
    INDEX `FK_maps_store` (`placeId`),
    CONSTRAINT `FK_maps_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COMMENT='地图信息'
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;






-- 导出  表 sva.svalist 结构
DROP TABLE IF EXISTS `svalist`;
CREATE TABLE IF NOT EXISTS `svalist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `ip` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL DEFAULT '0',
  `type` int(1) NOT NULL,
  `tokenPort` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `brokerPort` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idType` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mapLists` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `apiLists` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='sva列表';

-- 数据导出被取消选择。


-- 导出  表 sva.svastoremap 结构
DROP TABLE IF EXISTS `svastoremap`;
CREATE TABLE IF NOT EXISTS `svastoremap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `svaId` int(11) DEFAULT '0',
  `storeId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_svastoremap_svalist` (`svaId`),
  KEY `FK_svastoremap_store` (`storeId`),
  CONSTRAINT `FK_svastoremap_store` FOREIGN KEY (`storeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_svastoremap_svalist` FOREIGN KEY (`svaId`) REFERENCES `svalist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。



-- 导出  表 sva.role 结构
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `menukey` varchar(2000) DEFAULT NULL,
  `menus` varchar(255) DEFAULT NULL,
  `storesid` varchar(255) DEFAULT NULL,
  `stores` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。



-- 导出  表 sva.account 结构
DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 3` (`username`),
  KEY `fk_account_role` (`roleid`) USING BTREE,
  CONSTRAINT `fk_roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。


-- 导出  表 sva.accuracy 结构
DROP TABLE IF EXISTS `accuracy`;
CREATE TABLE IF NOT EXISTS `accuracy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) NOT NULL,
  `floorNo` decimal(10,2) NOT NULL,
  `origin` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `destination` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `triggerIp` varchar(50) CHARACTER SET utf8 NOT NULL,
  `offset` decimal(10,2) NOT NULL,
  `variance` decimal(10,2) NOT NULL,
  `averDevi` decimal(10,2) NOT NULL,
  `count_3` int(11) NOT NULL,
  `count_5` int(11) NOT NULL,
  `count_10` int(11) NOT NULL,
  `count_10p` int(11) NOT NULL,
  `detail` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.acquisitionpoint 结构
DROP TABLE IF EXISTS `acquisitionpoint`;
CREATE TABLE IF NOT EXISTS `acquisitionpoint` (
  `id` int(11) DEFAULT NULL,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `x1` decimal(10,0) DEFAULT NULL,
  `y1` decimal(10,0) DEFAULT NULL,
  `x2` decimal(10,0) DEFAULT NULL,
  `y2` decimal(10,0) DEFAULT NULL,
  `x3` decimal(10,0) DEFAULT NULL,
  `y3` decimal(10,0) DEFAULT NULL,
  `x` decimal(10,0) DEFAULT NULL,
  `y` decimal(10,0) DEFAULT NULL,
  `gx1` decimal(10,0) DEFAULT NULL,
  `gy1` decimal(10,0) DEFAULT NULL,
  `gx2` decimal(10,0) DEFAULT NULL,
  `gy2` decimal(10,0) DEFAULT NULL,
  `gx3` decimal(10,0) DEFAULT NULL,
  `gy3` decimal(10,0) DEFAULT NULL,
  `gx` decimal(10,0) DEFAULT NULL,
  `gy` decimal(10,0) DEFAULT NULL,
  `storeId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='坐标采集点';

-- 数据导出被取消选择。


-- 导出  表 sva.allpeople 结构
DROP TABLE IF EXISTS `allpeople`;
CREATE TABLE IF NOT EXISTS `allpeople` (
  `time` varchar(50) NOT NULL,
  `nowNumber` int(11) NOT NULL,
  `yesterNumber` int(11) NOT NULL,
  `placeId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`time`,`placeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='每分钟更新今日人数和昨日人数';

-- 数据导出被取消选择。


-- 导出  表 sva.category 结构
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 导出  表 sva.area 结构
DROP TABLE IF EXISTS `area`;
CREATE TABLE IF NOT EXISTS `area` (
    `areaName` VARCHAR(255) NOT NULL,
    `placeId` INT(11) NOT NULL,
    `id` INT(10) NOT NULL AUTO_INCREMENT,
    `floorNo` DECIMAL(10,2) NOT NULL,
    `xSpot` DECIMAL(10,2) NOT NULL,
    `ySpot` DECIMAL(10,2) NOT NULL,
    `status` INT(11) NOT NULL DEFAULT '0',
    `zoneId` VARCHAR(50) NULL DEFAULT NULL,
    `x1Spot` DECIMAL(10,2) NOT NULL,
    `y1Spot` DECIMAL(10,2) NOT NULL,
    `categoryid` INT(10) NOT NULL,
    `ISVIP` VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_message_maps` (`placeId`) USING BTREE,
    INDEX `FK_message_store` (`floorNo`) USING BTREE,
    INDEX `FK_message_category` (`categoryid`) USING BTREE,
    CONSTRAINT `area_ibfk_1` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT `area_ibfk_2` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT `area_ibfk_3` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

-- 数据导出被取消选择。


-- 导出  表 sva.bluemix 结构
DROP TABLE IF EXISTS `bluemix`;
CREATE TABLE IF NOT EXISTS `bluemix` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `svaUser` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `svaPassword` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `site` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ibmUser` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ibmPassword` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `tokenPort` int(11) NOT NULL,
  `brokerPort` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.bzprames 结构
DROP TABLE IF EXISTS `bzprames`;
CREATE TABLE IF NOT EXISTS `bzprames` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) NOT NULL,
  `placeId2` int(11) NOT NULL,
  `placeId2sp` int(11) NOT NULL,
  `placeId3` int(11) NOT NULL,
  `placeId3sp` int(11) NOT NULL,
  `floorNo` decimal(10,2) NOT NULL,
  `floorNo2` decimal(10,2) NOT NULL,
  `floorNo2sp` decimal(10,2) NOT NULL,
  `floorNo3` decimal(10,2) NOT NULL,
  `floorNo3sp` decimal(10,2) NOT NULL,
  `periodSel` int(10) NOT NULL,
  `startTime` datetime NOT NULL,
  `densitySel` int(11) DEFAULT NULL,
  `radiusSel` int(11) DEFAULT NULL,
  `densitySel1` int(11) DEFAULT NULL,
  `densitySel2` int(11) DEFAULT NULL,
  `radiusSel1` int(11) DEFAULT NULL,
  `radiusSel2` int(11) DEFAULT NULL,
  `coefficient` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fgffgf` (`placeId`) USING BTREE,
  KEY `dfdf` (`floorNo`) USING BTREE,
  KEY `fk_floorId2` (`floorNo2`),
  KEY `fk_floorId3` (`floorNo3`),
  KEY `fk_place2` (`placeId2`) USING BTREE,
  KEY `fk_floorId2sp` (`floorNo2sp`),
  KEY `fk_floorId3sp` (`floorNo3sp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。




-- 数据导出被取消选择。


-- 导出  表 sva.code 结构
DROP TABLE IF EXISTS `code`;
CREATE TABLE IF NOT EXISTS `code` (
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.district_during 结构
DROP TABLE IF EXISTS `district_during`;
CREATE TABLE IF NOT EXISTS `district_during` (
  `idType` varchar(50) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `time_begin` bigint(20) DEFAULT NULL,
  `time_local` bigint(20) DEFAULT NULL,
  `during` bigint(20) DEFAULT NULL,
  `dataType` varchar(50) DEFAULT NULL,
  `district_id` int(11) NOT NULL,
  `userID` varchar(50) NOT NULL,
  `loc_count` bigint(20) DEFAULT NULL,
  `departure_time` bigint(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.dynamicaccuracy 结构
DROP TABLE IF EXISTS `dynamicaccuracy`;
CREATE TABLE IF NOT EXISTS `dynamicaccuracy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) NOT NULL,
  `floorNo` decimal(10,2) NOT NULL,
  `origin` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `destination` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `avgeOffset` decimal(10,2) NOT NULL,
  `maxOffset` decimal(10,2) NOT NULL,
  `Offset` decimal(10,2) NOT NULL,
  `count_3` int(11) NOT NULL,
  `count_5` int(11) NOT NULL,
  `count_10` int(11) NOT NULL,
  `count_10p` int(11) NOT NULL,
  `detail` text COLLATE utf8_unicode_ci,
  `triggerIp` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.electronic 结构
DROP TABLE IF EXISTS `electronic`;
CREATE TABLE IF NOT EXISTS `electronic` (
  `placeId` int(11) NOT NULL,
  `electronicName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` mediumtext CHARACTER SET utf8,
  `shopId` int(11) DEFAULT NULL,
  `pictruePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `moviePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `floorNo` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_electronic_store` (`placeId`),
  KEY `FK_electronic_maps` (`floorNo`),
  KEY `FK_electronic_area` (`shopId`),
  CONSTRAINT `FK_electronic_area` FOREIGN KEY (`shopId`) REFERENCES `area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_electronic_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_electronic_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.estimatedev 结构
DROP TABLE IF EXISTS `estimatedev`;
CREATE TABLE IF NOT EXISTS `estimatedev` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) DEFAULT NULL,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `a` decimal(10,2) DEFAULT NULL,
  `b` decimal(10,2) DEFAULT NULL,
  `n` int(11) DEFAULT NULL,
  `type` decimal(10,3) DEFAULT NULL,
  `d` decimal(10,1) DEFAULT NULL,
  `deviation` decimal(10,1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `FK_estimatedev_store` (`placeId`),
  KEY `FK_estimatedev_maps` (`floorNo`),
  CONSTRAINT `FK_estimatedev_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_estimatedev_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='预估偏差';

-- 数据导出被取消选择。


-- 导出  表 sva.geofencing 结构
DROP TABLE IF EXISTS `geofencing`;
CREATE TABLE IF NOT EXISTS `geofencing` (
  `IdType` varchar(50) NOT NULL,
  `time_local` varchar(50) DEFAULT NULL,
  `userid` varchar(50) NOT NULL,
  `mapid` bigint(20) NOT NULL,
  `zoneid` bigint(20) NOT NULL,
  `enter` varchar(50) NOT NULL,
  `Timestamp` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.info_cgi_mapper 结构
DROP TABLE IF EXISTS `info_cgi_mapper`;
CREATE TABLE IF NOT EXISTS `info_cgi_mapper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cgi` varchar(50) NOT NULL DEFAULT '0',
  `svaId` int(11) NOT NULL,
  KEY `Index 1` (`id`),
  KEY `FK_info_cgi_mapper_svalist` (`svaId`),
  CONSTRAINT `FK_info_cgi_mapper_svalist` FOREIGN KEY (`svaId`) REFERENCES `svalist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='获取定位信息表';

-- 数据导出被取消选择。


-- 导出  表 sva.info_register 结构
DROP TABLE IF EXISTS `info_register`;
CREATE TABLE IF NOT EXISTS `info_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plmn` varchar(200) NOT NULL,
  `ils_ip` varchar(300) NOT NULL,
  `ils_url` varchar(300) NOT NULL,
  KEY `Index 1` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。



-- 导出  表 sva.locationdelay 结构
DROP TABLE IF EXISTS `locationdelay`;
CREATE TABLE IF NOT EXISTS `locationdelay` (
  `id` int(11) NOT NULL DEFAULT '0',
  `placeId` int(11) DEFAULT NULL,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `dataDelay` double DEFAULT NULL,
  `positionDelay` double DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.locationphone 结构
DROP TABLE IF EXISTS `locationphone`;
CREATE TABLE IF NOT EXISTS `locationphone` (
  `idType` varchar(50) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `time_begin` bigint(20) DEFAULT NULL,
  `time_local` bigint(20) DEFAULT NULL,
  `during` bigint(20) NOT NULL DEFAULT '0',
  `dataType` varchar(50) DEFAULT NULL,
  `x` decimal(10,0) DEFAULT NULL,
  `y` decimal(10,0) DEFAULT NULL,
  `z` decimal(10,2) NOT NULL DEFAULT '0.00',
  `userID` varchar(50) NOT NULL,
  `loc_count` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`userID`,`z`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.locationtemp 结构
DROP TABLE IF EXISTS `locationtemp`;
CREATE TABLE IF NOT EXISTS `locationtemp` (
  `idType` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dataType` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `x` decimal(10,0) DEFAULT NULL,
  `y` decimal(10,0) DEFAULT NULL,
  `z` decimal(10,0) DEFAULT NULL,
  `userID` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  KEY `Index 1` (`userID`),
  KEY `Index 2` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。




-- 导出  表 sva.menu 结构
DROP TABLE IF EXISTS `menu`;
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `url` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `parent` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `type` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。


-- 导出  表 sva.menuenglish 结构
DROP TABLE IF EXISTS `menuenglish`;
CREATE TABLE IF NOT EXISTS `menuenglish` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `keyEN` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。


-- 导出  表 sva.menuname 结构
DROP TABLE IF EXISTS `menuname`;
CREATE TABLE IF NOT EXISTS `menuname` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `keyZH` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。


-- 导出  表 sva.message 结构
DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `placeId` int(11) NOT NULL,
  `shopName` varchar(100) CHARACTER SET utf8 NOT NULL,
  `xSpot` decimal(10,2) NOT NULL,
  `ySpot` decimal(10,2) NOT NULL,
  `rangeSpot` decimal(10,2) DEFAULT NULL,
  `timeInterval` int(10) DEFAULT NULL,
  `message` mediumtext CHARACTER SET utf8,
  `isEnable` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `pictruePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `moviePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `x1Spot` decimal(10,2) DEFAULT NULL,
  `y1Spot` decimal(10,2) DEFAULT NULL,
  `shopId` int(11) DEFAULT NULL,
  `ticketPath` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'null',
  PRIMARY KEY (`id`),
  KEY `FK_message_maps` (`floorNo`),
  KEY `FK_message_store` (`placeId`),
  CONSTRAINT `FK_message_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_message_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.messagepush 结构
DROP TABLE IF EXISTS `messagepush`;
CREATE TABLE IF NOT EXISTS `messagepush` (
  `id` int(11) NOT NULL DEFAULT '0',
  `placeId` int(11) DEFAULT NULL,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `pushRight` double DEFAULT NULL,
  `pushWrong` double DEFAULT NULL,
  `notPush` double DEFAULT NULL,
  `centerRadius` varchar(100) DEFAULT NULL,
  `centerReality` varchar(100) DEFAULT NULL,
  `isRigth` int(11) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.mwcprames 结构
DROP TABLE IF EXISTS `mwcprames`;
CREATE TABLE IF NOT EXISTS `mwcprames` (
  `floorNo1` decimal(10,2) DEFAULT NULL,
  `floorNo2` decimal(10,2) DEFAULT NULL,
  `floorNo3` decimal(10,2) DEFAULT NULL,
  `floorNo4` decimal(10,2) DEFAULT NULL,
  `floorNo5` decimal(10,2) DEFAULT NULL,
  `floorNo6` decimal(10,2) DEFAULT NULL,
  `floorNo7` decimal(10,2) DEFAULT NULL,
  `floorNo8` decimal(10,2) DEFAULT NULL,
  `densitySel1` int(11) DEFAULT NULL,
  `densitySel2` int(11) DEFAULT NULL,
  `densitySel3` int(11) DEFAULT NULL,
  `densitySel4` int(11) DEFAULT NULL,
  `densitySel5` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `densitySel6` int(11) DEFAULT NULL,
  `densitySel7` int(11) DEFAULT NULL,
  `densitySel` int(11) DEFAULT NULL,
  `radiusSel1` int(11) DEFAULT NULL,
  `radiusSel2` int(11) DEFAULT NULL,
  `radiusSel3` int(11) DEFAULT NULL,
  `coefficient` int(11) DEFAULT NULL,
  `radiusSel4` int(11) DEFAULT NULL,
  `radiusSel5` int(11) DEFAULT NULL,
  `radiusSel6` int(11) DEFAULT NULL,
  `radiusSel7` int(11) DEFAULT NULL,
  `radiusSel` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL DEFAULT '0',
  `periodSel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.nowpeople 结构
DROP TABLE IF EXISTS `nowpeople`;
CREATE TABLE IF NOT EXISTS `nowpeople` (
  `areaName` varchar(50) NOT NULL,
  `areaId` int(11) NOT NULL,
  `placeId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`areaId`,`placeId`),
  CONSTRAINT `area_name` FOREIGN KEY (`areaId`) REFERENCES `area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 sva.param 结构
DROP TABLE IF EXISTS `param`;
CREATE TABLE IF NOT EXISTS `param` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `banThreshold` double(20,2) NOT NULL,
  `filterLength` double(20,2) NOT NULL,
  `horizontalWeight` double(20,2) unsigned NOT NULL,
  `ongitudinalWeight` double(20,2) NOT NULL,
  `maxDeviation` double(20,2) NOT NULL,
  `exceedMaxDeviation` double(20,2) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `correctMapDirection` double(20,2) NOT NULL,
  `restTimes` double(20,2) NOT NULL,
  `step` double(20,2) NOT NULL,
  `filterPeakError` double(20,2) NOT NULL,
  `peakWidth` double(20,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。


-- 导出  表 sva.parkinginformation 结构
DROP TABLE IF EXISTS `parkinginformation`;
CREATE TABLE IF NOT EXISTS `parkinginformation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storeId` int(11) NOT NULL DEFAULT '0',
  `parkingNumber` int(11) NOT NULL,
  `entryTime` bigint(20) DEFAULT NULL,
  `outTime` bigint(20) DEFAULT NULL,
  `plateNumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `floorNo` decimal(10,2) NOT NULL,
  `isTrue` int(11) NOT NULL DEFAULT '0' COMMENT '0：空车位，1：非空车位',
  `userName` varchar(50) DEFAULT NULL,
  `x` decimal(10,2) DEFAULT NULL,
  `y` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`floorNo`,`parkingNumber`),
  KEY `Index 1` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='0:空车位  1：非空车位';

-- 数据导出被取消选择。


-- 导出  表 sva.petlocation 结构
DROP TABLE IF EXISTS `petlocation`;
CREATE TABLE IF NOT EXISTS `petlocation` (
  `x` decimal(10,2) NOT NULL,
  `y` decimal(10,2) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `petRefreshTime` bigint(20) NOT NULL DEFAULT '0',
  `z` decimal(10,0) NOT NULL,
  `count` int(11) NOT NULL,
  `petTypes` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `actualPositionX` decimal(10,2) NOT NULL,
  `actualPositionY` decimal(10,2) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.petsproperties 结构
DROP TABLE IF EXISTS `petsproperties`;
CREATE TABLE IF NOT EXISTS `petsproperties` (
  `probability` decimal(10,0) DEFAULT NULL,
  `viewRange` decimal(10,0) DEFAULT NULL,
  `captureRange` decimal(10,0) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `petName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='宠物属性表';

-- 数据导出被取消选择。


-- 导出  表 sva.phonenumber 结构
DROP TABLE IF EXISTS `phonenumber`;
CREATE TABLE IF NOT EXISTS `phonenumber` (
  `ip` varchar(50) NOT NULL,
  `phoneNumber` varchar(50) DEFAULT NULL,
  `timestamp` bigint(20) NOT NULL,
  UNIQUE KEY `Index 1` (`ip`,`phoneNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 sva.prru 结构
DROP TABLE IF EXISTS `prru`;
CREATE TABLE IF NOT EXISTS `prru` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pRRUid` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `x` varchar(50) DEFAULT NULL,
  `y` varchar(50) DEFAULT NULL,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `placeId` int(11) DEFAULT NULL,
  `neCode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_prru_maps` (`floorNo`),
  KEY `FK_prru_store` (`placeId`),
  CONSTRAINT `FK_prru_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_prru_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.prrufeature 结构
DROP TABLE IF EXISTS `prrufeature`;
CREATE TABLE IF NOT EXISTS `prrufeature` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `x` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'x坐标（米）',
  `y` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'y坐标（米）',
  `floorNo` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '楼层id',
  `checkValue` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '校验值',
  `featureRadius` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '特征半径',
  `userId` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户id',
  `timestamp` bigint(20) NOT NULL DEFAULT '0' COMMENT '插入时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='prru特征库\r\n';

-- 数据导出被取消选择。


-- 导出  表 sva.prrufeaturedetail 结构
DROP TABLE IF EXISTS `prrufeaturedetail`;
CREATE TABLE IF NOT EXISTS `prrufeaturedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `featureId` int(11) NOT NULL DEFAULT '0' COMMENT '外键特征库id',
  `gpp` varchar(50) NOT NULL DEFAULT '0' COMMENT '柜框槽号',
  `featureValue` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '特征值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='详细的特征值信息';

-- 数据导出被取消选择。


-- 导出  表 sva.prrusignal 结构
DROP TABLE IF EXISTS `prrusignal`;
CREATE TABLE IF NOT EXISTS `prrusignal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `gpp` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '柜框槽号',
  `rsrp` decimal(10,2) DEFAULT NULL COMMENT '信号值',
  `userId` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户id',
  `enbid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'enodeBid',
  `timestamp` bigint(20) DEFAULT NULL COMMENT '插入时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='prru信号数据';

-- 数据导出被取消选择。


-- 导出  表 sva.pushmsg 结构
DROP TABLE IF EXISTS `pushmsg`;
CREATE TABLE IF NOT EXISTS `pushmsg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户id',
  `content` varchar(100) DEFAULT '0' COMMENT '推送消息内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务器端推送消息，一个userid对应多条消息';

-- 数据导出被取消选择。



-- 导出  表 sva.register 结构
DROP TABLE IF EXISTS `register`;
CREATE TABLE IF NOT EXISTS `register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(50) DEFAULT NULL,
  `info` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `loginStatus` varchar(50) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `password` varchar(50) DEFAULT NULL,
  `phoneNumber` bigint(20) DEFAULT NULL,
  `role` int(11) DEFAULT NULL COMMENT '0:  1:  2:',
  `isTrue` int(11) DEFAULT '0',
  `otherPhone` varchar(50) DEFAULT NULL,
  UNIQUE KEY `55555` (`phoneNumber`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。




-- 导出  表 sva.seller 结构
DROP TABLE IF EXISTS `seller`;
CREATE TABLE IF NOT EXISTS `seller` (
  `placeId` int(11) NOT NULL,
  `xSpot` decimal(10,2) NOT NULL,
  `ySpot` decimal(10,2) NOT NULL,
  `info` mediumtext CHARACTER SET utf8,
  `isEnable` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `pictruePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `moviePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isVip` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `floorNo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_seller_store` (`placeId`),
  KEY `FK_seller_maps` (`floorNo`),
  CONSTRAINT `FK_seller_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_seller_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.shprames 结构
DROP TABLE IF EXISTS `shprames`;
CREATE TABLE IF NOT EXISTS `shprames` (
  `floorNo1` decimal(10,2) DEFAULT NULL,
  `floorNo2` decimal(10,2) DEFAULT NULL,
  `floorNo3` decimal(10,2) DEFAULT NULL,
  `densitySel1` int(11) DEFAULT NULL,
  `densitySel2` int(11) DEFAULT NULL,
  `densitySel3` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `radiusSel1` int(11) DEFAULT NULL,
  `radiusSel2` int(11) DEFAULT NULL,
  `radiusSel3` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL DEFAULT '0',
  `coefficient` int(11) NOT NULL DEFAULT '0',
  `periodSel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.shpramesjing 结构
DROP TABLE IF EXISTS `shpramesjing`;
CREATE TABLE IF NOT EXISTS `shpramesjing` (
  `floorNo1` decimal(10,2) DEFAULT NULL,
  `floorNo2` decimal(10,2) DEFAULT NULL,
  `floorNo3` decimal(10,2) DEFAULT NULL,
  `floorNo4` decimal(10,2) DEFAULT NULL,
  `densitySel1` int(11) DEFAULT NULL,
  `densitySel2` int(11) DEFAULT NULL,
  `densitySel3` int(11) DEFAULT NULL,
  `densitySel4` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `radiusSel1` int(11) DEFAULT NULL,
  `radiusSel2` int(11) DEFAULT NULL,
  `radiusSel3` int(11) DEFAULT NULL,
  `radiusSel4` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL DEFAULT '0',
  `coefficient` int(11) NOT NULL DEFAULT '0',
  `periodSel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.staticaccuracy 结构
DROP TABLE IF EXISTS `staticaccuracy`;
CREATE TABLE IF NOT EXISTS `staticaccuracy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) NOT NULL,
  `floorNo` decimal(10,2) NOT NULL,
  `origin` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `destination` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `avgeOffset` decimal(10,0) NOT NULL,
  `maxOffset` decimal(10,2) NOT NULL,
  `staicAccuracy` decimal(10,2) NOT NULL,
  `offsetCenter` decimal(10,2) NOT NULL,
  `offsetNumber` decimal(10,2) NOT NULL,
  `stability` decimal(10,0) NOT NULL,
  `count_3` int(11) NOT NULL,
  `count_5` int(11) NOT NULL,
  `count_10` int(11) NOT NULL,
  `count_10p` int(11) NOT NULL,
  `detail` text COLLATE utf8_unicode_ci,
  `triggerIp` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.staticvisit 结构
DROP TABLE IF EXISTS `staticvisit`;
CREATE TABLE IF NOT EXISTS `staticvisit` (
  `areaId` int(11) NOT NULL,
  `time` varchar(50) NOT NULL,
  `allTime` bigint(20) NOT NULL,
  `number` int(11) NOT NULL,
  `averageTime` bigint(20) NOT NULL,
  PRIMARY KEY (`areaId`,`time`),
  CONSTRAINT `areaId` FOREIGN KEY (`areaId`) REFERENCES `area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.statisticarea 结构
DROP TABLE IF EXISTS `statisticarea`;
CREATE TABLE IF NOT EXISTS `statisticarea` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `areaId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.statisticareaday 结构
DROP TABLE IF EXISTS `statisticareaday`;
CREATE TABLE IF NOT EXISTS `statisticareaday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `areaId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.statisticday 结构
DROP TABLE IF EXISTS `statisticday`;
CREATE TABLE IF NOT EXISTS `statisticday` (
  `placeId` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`placeId`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.statisticfloor 结构
DROP TABLE IF EXISTS `statisticfloor`;
CREATE TABLE IF NOT EXISTS `statisticfloor` (
  `userID` varchar(50) CHARACTER SET utf8 NOT NULL,
  `time` datetime NOT NULL,
  `z` decimal(10,0) NOT NULL,
  PRIMARY KEY (`userID`,`time`,`z`),
  KEY `index` (`z`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.statistichour 结构
DROP TABLE IF EXISTS `statistichour`;
CREATE TABLE IF NOT EXISTS `statistichour` (
  `placeId` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`placeId`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.statisticlinetemp 结构
DROP TABLE IF EXISTS `statisticlinetemp`;
CREATE TABLE IF NOT EXISTS `statisticlinetemp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) NOT NULL,
  `time` datetime DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 sva.statistictemp 结构
DROP TABLE IF EXISTS `statistictemp`;
CREATE TABLE IF NOT EXISTS `statistictemp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '0',
  `number` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。





-- 导出  表 sva.ticket 结构
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msgId` varchar(50) DEFAULT NULL,
  `chances` varchar(50) DEFAULT NULL,
  `ticketPath` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.website_visit 结构
DROP TABLE IF EXISTS `website_visit`;
CREATE TABLE IF NOT EXISTS `website_visit` (
  `visitCount` int(11) DEFAULT '0',
  `ip` varchar(50) DEFAULT NULL,
  `last_visitTime` bigint(20) DEFAULT NULL,
  `first_visitTime` bigint(20) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `iosCount` int(11) DEFAULT '0',
  `androidCount` int(11) DEFAULT '0',
  `webCount` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


INSERT INTO `role` (`id`, `name`, `menukey`, `menus`, `storesid`, `stores`, `updateTime`) VALUES
    (1, '1', '1', '1', '1', '1', '2016-06-25 05:28:33');
INSERT INTO `account` (`id`, `roleid`, `username`, `password`, `updateTime`) VALUES
    (1, 1, 'admin', 'admin', '2016-06-25 05:28:55');
INSERT INTO `code` (`name`, `password`) VALUES
    ('admin', 'admin');
INSERT INTO `menuenglish` (`id`, `keyEN`, `name`) VALUES
    (1, 'key_storeManage', 'Store Management'),
    (2, 'key_svaManage', 'SVA Management'),
    (3, 'key_mapManage', 'Map Management'),
    (4, 'key_messagePush', 'Message Management'),
    (5, 'key_sellerInfo', 'Seller Management'),
    (6, 'key_areaCategory', ' Regional category management '),
    (7, 'key_areaInfo', 'Regional information input'),
    (8, 'key_customerHeamap', 'Customer Heatmap'),
    (9, 'key_customerPeriodHeamap', 'Customer Heatmap in Period'),
    (10,'key_customerScattermap', 'Customer Scattermap'),
    (11,'key_historicalTrack', 'Historical Track'),
    (12,'key_CustomerFlowLinemap', 'Customer Flow Range Linemap'),
    (13,'key_code', 'Code Management'),
    (14,'key_estimateDeviation', 'Set Estimate Deviation'),
    (15,'key_summaryDisplay', 'Summary Display'),
    (16,'key_bluemixConnection', 'Bluemix Connection'),
    (17,'key_ping', 'ping'),
    (18,'key_pRRUConfig', 'pRRU Config'),
    (19, 'key_versionDownload', 'Version Downloads'),
    (20, 'key_APKDownload', 'APKDownload'),
    (21, 'key_role', 'Role management'),
    (22, 'key_paramConfig', 'Parameter configuration '),
    (23, 'key_account', 'Rights management'),
    (24, 'key_allShow', 'Global overview'),
    (25, 'key_dynamicAccuyacy', 'Dynamic accuracy test'),
    (26, 'key_staticAccuyacy', 'Static accuracy test'),
    (27, 'key_positionlatency', ' Position latency'),
    (28, 'key_MessagePush', 'Message push accuracy'),
    (29, 'key_positionMap', 'Position Distribution Map');
INSERT INTO `menuname` (`id`, `keyZH`, `name`) VALUES
    (1, 'key_storeManage', '商场管理'),
    (2, 'key_svaManage', 'SVA管理'),
    (3, 'key_mapManage', '地图管理'),
    (4, 'key_messagePush', '消息推送管理'),
    (5, 'key_sellerInfo', '商户信息管理'),
    (6, 'key_areaCategory', '区域类别管理'),
    (7, 'key_areaInfo', '区域信息录入'),
    (8, 'key_customerHeamap', '客流实时热力图'),
    (9, 'key_customerPeriodHeamap', '时间段客流热力图'),
    (10, 'key_customerScattermap', '客流实时散点图'),
    (11, 'key_historicalTrack', '历史轨迹'),
    (12, 'key_CustomerFlowLinemap', '历史客流分析图'),
    (13, 'key_code', '口令管理'),
    (14, 'key_estimateDeviation', '预估偏差设定'),
    (15, 'key_summaryDisplay', '汇总结果'),
    (16, 'key_bluemixConnection', 'bluemix对接'),
    (17, 'key_ping', 'ping'),
    (18, 'key_pRRUConfig', 'pRRU配置'),
    (19, 'key_versionDownload', '版本下载'),
    (20, 'key_APKDownload', 'APK下载'),
    (21, 'key_role', '角色管理'),
    (22, 'key_account', '权限管理'),
    (23, 'key_paramConfig', '参数配置'),
    (24, 'key_allShow', '全局概览'),
    (25, 'key_dynamicAccuyacy', '动态精度测试'),
    (26, 'key_staticAccuyacy', '静态精度测试'),
    (27, 'key_positionlatency', '定位延时'),
    (28, 'key_MessagePush', '消息推送准确率'),
    (29, 'key_positionMap', '手机号归属地分布图');
    
    INSERT INTO `param` (`id`, `banThreshold`, `filterLength`, `horizontalWeight`, `ongitudinalWeight`, `maxDeviation`, `exceedMaxDeviation`, `updateTime`, `correctMapDirection`, `restTimes`, `step`, `filterPeakError`, `peakWidth`) VALUES
    (1, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1, 1.00, 1.00, 1.00, 1.00, 1.00);

CREATE TABLE `petsproperties` (
    `probability` DECIMAL(10,0) NULL DEFAULT NULL,
    `viewRange` DECIMAL(10,0) NULL DEFAULT NULL,
    `captureRange` DECIMAL(10,0) NULL DEFAULT NULL,
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `petName` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    `count` INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
COMMENT='宠物属性表'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;


CREATE TABLE `acquisitionpoint` (
    `id` INT(11) NULL DEFAULT NULL,
    `floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
    `x1` DECIMAL(10,0) NULL DEFAULT NULL,
    `y1` DECIMAL(10,0) NULL DEFAULT NULL,
    `x2` DECIMAL(10,0) NULL DEFAULT NULL,
    `y2` DECIMAL(10,0) NULL DEFAULT NULL,
    `x3` DECIMAL(10,0) NULL DEFAULT NULL,
    `y3` DECIMAL(10,0) NULL DEFAULT NULL,
    `x` DECIMAL(10,0) NULL DEFAULT NULL,
    `y` DECIMAL(10,0) NULL DEFAULT NULL,
    `gx1` DECIMAL(10,0) NULL DEFAULT NULL,
    `gy1` DECIMAL(10,0) NULL DEFAULT NULL,
    `gx2` DECIMAL(10,0) NULL DEFAULT NULL,
    `gy2` DECIMAL(10,0) NULL DEFAULT NULL,
    `gx3` DECIMAL(10,0) NULL DEFAULT NULL,
    `gy3` DECIMAL(10,0) NULL DEFAULT NULL,
    `gx` DECIMAL(10,0) NULL DEFAULT NULL,
    `gy` DECIMAL(10,0) NULL DEFAULT NULL,
    `storeId` INT(11) NULL DEFAULT NULL
)
COMMENT='坐标采集点'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;


CREATE TABLE `info_cgi_mapper` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `cgi` VARCHAR(50) NOT NULL DEFAULT '0',
    `svaId` INT(11) NOT NULL,
    INDEX `Index 1` (`id`),
    INDEX `FK_info_cgi_mapper_svalist` (`svaId`),
    CONSTRAINT `FK_info_cgi_mapper_svalist` FOREIGN KEY (`svaId`) REFERENCES `svalist` (`id`)
)
COMMENT='获取定位信息表'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

CREATE TABLE `info_register` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `plmn` VARCHAR(200) NOT NULL,
    `ils_ip` VARCHAR(300) NOT NULL,
    `ils_url` VARCHAR(300) NOT NULL,
    INDEX `Index 1` (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

CREATE TABLE `parkinginformation` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `storeId` INT(11) NOT NULL DEFAULT '0',
    `parkingNumber` INT(11) NOT NULL,
    `entryTime` BIGINT(20) NULL DEFAULT NULL,
    `outTime` BIGINT(20) NULL DEFAULT NULL,
    `plateNumber` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `floorNo` DECIMAL(10,2) NOT NULL,
    `isTrue` INT(11) NOT NULL DEFAULT '0' COMMENT '0：空车位，1：非空车位',
    `userName` VARCHAR(50) NULL DEFAULT NULL,
    `x` DECIMAL(10,2) NULL DEFAULT NULL,
    `y` DECIMAL(10,2) NULL DEFAULT NULL,
    PRIMARY KEY (`floorNo`, `parkingNumber`),
    INDEX `Index 1` (`id`)
)
COMMENT='0:空车位  1：非空车位'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

CREATE TABLE `prrufeature` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `x` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT 'x坐标（米）',
    `y` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT 'y坐标（米）',
    `floorNo` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '楼层id',
    `checkValue` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '校验值',
    `featureRadius` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '特征半径',
    `userId` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '用户id',
    `timestamp` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '插入时间戳',
    PRIMARY KEY (`id`)
)
COMMENT='prru特征库\r\n'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;


CREATE TABLE `prrufeaturedetail` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `featureId` INT(11) NOT NULL DEFAULT '0' COMMENT '外键特征库id',
    `gpp` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '柜框槽号',
    `featureValue` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '特征值',
    PRIMARY KEY (`id`)
)
COMMENT='详细的特征值信息'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

CREATE TABLE `prrusignal` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `gpp` VARCHAR(50) NULL DEFAULT NULL COMMENT '柜框槽号' COLLATE 'utf8_unicode_ci',
    `rsrp` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '信号值',
    `userId` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户id' COLLATE 'utf8_unicode_ci',
    `enbid` VARCHAR(50) NULL DEFAULT NULL COMMENT 'enodeBid' COLLATE 'utf8_unicode_ci',
    `timestamp` BIGINT(20) NULL DEFAULT NULL COMMENT '插入时间戳',
    PRIMARY KEY (`id`)
)
COMMENT='prru信号数据'
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;


