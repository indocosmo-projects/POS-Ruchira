

-- ----------------------------
-- Table structure for db_version
-- ----------------------------
DROP TABLE IF EXISTS `about`;
CREATE TABLE `about` (
  `contents` text COLLATE utf8_unicode_ci,
  `copyright` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- ----------------------------
-- Table structure for db_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `major` int(3) DEFAULT NULL,
  `minor` int(3) DEFAULT NULL,
  `patch`  int(3) NULL,
  `build_no` VARCHAR(50) DEFAULT NULL,
  `build_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Values for db_version 

INSERT INTO `app_version` (`major`, `minor`, `patch`) VALUES (5,0,0);


-- ----------------------------
-- Table structure for access_log
-- ----------------------------
DROP TABLE IF EXISTS `access_log`;
CREATE TABLE `access_log` (
  `function_name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `user_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `access_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for addressbook
-- ----------------------------
DROP TABLE IF EXISTS `addressbook`;
CREATE TABLE `addressbook` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` bigint(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for area_codes
-- ----------------------------
DROP TABLE IF EXISTS `area_codes`;
CREATE TABLE `area_codes` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(251) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',  
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for bank_card_types
-- ----------------------------
DROP TABLE IF EXISTS `bank_card_types`;
CREATE TABLE `bank_card_types` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_valid` tinyint(1) NOT NULL,
  `account_code` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `iin_prefix_range_from` bigint(20) DEFAULT NULL,
  `iin_prefix_range_to` bigint(20) DEFAULT NULL,
  `is_refundable` tinyint(1) DEFAULT NULL,
  `alternative_refund_method` tinyint(4) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `iin_prefix` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for bill_params
-- ----------------------------
DROP TABLE IF EXISTS `bill_params`;
CREATE TABLE `bill_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `show_discount_summary` tinyint(1) NOT NULL DEFAULT '0',
  `show_tax_summary` tinyint(1) NOT NULL,
  `show_item_discount` tinyint(1) NOT NULL DEFAULT '0',
  `show_item_tax` tinyint(1) NOT NULL,
  `bill_tax_id` bigint(20) DEFAULT NULL,
  `bill_hdr1` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `bill_hdr2` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr3` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr4` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr5` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr6` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr7` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr8` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr9` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_hdr10` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer1` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer2` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer3` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer4` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer5` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer6` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer7` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer8` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer9` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_footer10` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rounding_id` bigint(20) DEFAULT NULL,
  `is_system`  tinyint(1) NOT NULL DEFAULT 0,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for card_payment_recovery
-- ----------------------------
DROP TABLE IF EXISTS `card_payment_recovery`;
CREATE TABLE `card_payment_recovery` (
  `eft_purchase_message` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for cashier_shifts
-- ----------------------------
DROP TABLE IF EXISTS `cashier_shifts`;
CREATE TABLE `cashier_shifts` (
  `auto_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cashier_id` bigint(20) NOT NULL,
  `pos_id` bigint(14) NOT NULL DEFAULT '0',
  `opening_date` date DEFAULT NULL COMMENT 'POS open date',
  `opening_time` datetime DEFAULT NULL COMMENT 'Current system date and time ',
  `shift_id` bigint(20) NOT NULL,
  `opening_till_id` bigint(20) NULL COMMENT 'Till  ID to which this record is attached',
  `opening_float` decimal(14,5) DEFAULT NULL,
  `closing_date` date DEFAULT NULL COMMENT 'POS closing date',
  `closing_time` datetime DEFAULT NULL COMMENT 'Current system date and time',
  `collection_cash` decimal(14,5) DEFAULT NULL,
  `collection_card` decimal(14,5) DEFAULT NULL,
  `collection_voucher` decimal(14,5) DEFAULT NULL,
  `collection_company` decimal(14,5) DEFAULT NULL,
  `collection_online` decimal(14,5) DEFAULT NULL,
  `daily_cashout` decimal(14,5) DEFAULT NULL,
  `closing_float` decimal(14,5) DEFAULT NULL,
  `balance_cash` decimal(14,5) DEFAULT '0.00',
  `balance_voucher` decimal(14,5) DEFAULT '0.00',
  `balance_voucher_returned` decimal(14,5) DEFAULT '0.00',
  `cash_out` decimal(14,5) DEFAULT '0.00',
  `cash_refund` decimal(14,5) DEFAULT '0.00',
  `card_refund` decimal(14,5) DEFAULT '0.00',
  `voucher_refund` decimal(14,5) DEFAULT '0.00',
  `company_refund` decimal(14,5) DEFAULT '0.00',
  `online_refund` decimal(14,5) DEFAULT '0.00000',
  `total_refund` decimal(14,5) DEFAULT '0.00',
  `sync_status` tinyint(4) DEFAULT '0',
  `sync_message` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_open_till` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`auto_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for choices
-- ----------------------------
DROP TABLE IF EXISTS `choices`;
CREATE TABLE `choices` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(250)  NULL,
  `is_global` tinyint(1) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for combo_contents
-- ----------------------------
DROP TABLE IF EXISTS `combo_contents`;
CREATE TABLE `combo_contents` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(251) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uom_id` bigint(20) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for combo_content_substitutions
-- ----------------------------
DROP TABLE IF EXISTS `combo_content_substitutions`;
CREATE TABLE `combo_content_substitutions` (
  `id` bigint(20) NOT NULL,
  `combo_content_id` bigint(20) NOT NULL,
  `substitution_sale_item_id` bigint(20) NOT NULL,
  `price_diff` decimal(14,5) NOT NULL,
  `qty` decimal(14,5) DEFAULT '1',
  `is_default` tinyint(4) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for counter
-- ----------------------------
DROP TABLE IF EXISTS `counter`;
CREATE TABLE `counter` (
  `module` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `key_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `key_value` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`module`, `key_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for station_counter
-- ----------------------------
DROP TABLE IF EXISTS `station_counter`;
CREATE TABLE `station_counter` (
 `station_id`  varchar(50) NOT NULL ,
 `module`  varchar(50)  NOT NULL ,
 `key_name`  varchar(50) NOT NULL ,
 `key_value`  bigint(20) NULL DEFAULT NULL ,
 PRIMARY KEY (`station_id`,`module`, `key_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for currencies
-- ----------------------------
DROP TABLE IF EXISTS `currencies`;
CREATE TABLE `currencies` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `symbol` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fraction_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fraction_symbol` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL,
  `decimal_places` tinyint(4) DEFAULT NULL,
  `rounding_id` bigint(20) NOT NULL,
  `is_base_currency` tinyint(1) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `exchange_rate_at` date DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for customer_types
-- ----------------------------
DROP TABLE IF EXISTS `customer_types`;
CREATE TABLE `customer_types` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_ar` tinyint(1) NOT NULL DEFAULT 0,
  `default_price_variance_pc` decimal(14,5) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `category` VARCHAR (2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `customer_type` int(11) NOT NULL,
  `is_valid` tinyint(1) NOT NULL,
  `card_no` varchar(100) COLLATE utf8_unicode_ci NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address2` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address3` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address4` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `street` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_ar` tinyint(1) NOT NULL DEFAULT 0,
  `ar_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `joining_date` date DEFAULT NULL,
  `accumulated_points` bigint(20) DEFAULT NULL,
  `redeemed_points` bigint(20) DEFAULT NULL,
  `cst_no`  varchar(255) COLLATE utf8_unicode_ci NULL,
  `license_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tin`  varchar(255) COLLATE utf8_unicode_ci NULL,
  `gst_reg_type`  bigint(20) DEFAULT 2,
  `gst_party_type`  bigint(20) DEFAULT 1,
  `bank_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_branch` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_ifsc_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_micr_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_account_no` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for customers_type_item_prices
-- ----------------------------
DROP TABLE IF EXISTS `customers_type_item_prices`;
CREATE TABLE `customers_type_item_prices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_type_id` bigint(20) NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `price_variance_pc` decimal(14,5) NOT NULL COMMENT 'contains variance amount or fixed amount',
  `is_price_variance` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'price_variance_pc is variance amount or fixed amount',
  `is_percentage` tinyint(1) NOT NULL DEFAULT '1',
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for dashboard_sales
-- ----------------------------
DROP TABLE IF EXISTS `dashboard_sales`;
CREATE TABLE `dashboard_sales` (
  `sales_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sales_date` date DEFAULT NULL,
  `sales_amount` decimal(14,5) DEFAULT NULL,
  `sales_target` decimal(14,5) DEFAULT NULL,
  `item_sold` int(11) DEFAULT '0',
  `transactions` int(11) DEFAULT '0',
  `staff_hours` varchar(50) COLLATE utf8_unicode_ci DEFAULT '00:00:00',
  `staff_hours_target` varchar(20) COLLATE utf8_unicode_ci DEFAULT '00:00:00',
  `labour_cost` decimal(14,5) DEFAULT '0.00',
  `labour_cost_target` decimal(14,5) DEFAULT '0.00',
  `total_tax` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'total_tax1+total_tax2+total_tax3+total_gst+total_sc',
  `sales_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 -> open, 1 -> closed',
  `total_extras` DECIMAL(14,5) NOT NULL DEFAULT '0',
  `total_bill_discount`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `total_detail_discount`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `total_refund_amt`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `total_bill_discount_tax`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `refund_tax`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `total_extracharge_tax`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  PRIMARY KEY (`sales_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for day_process
-- ----------------------------
DROP TABLE IF EXISTS `day_process`;
CREATE TABLE `day_process` (
  `shop_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `pos_date` date NOT NULL,
  `day_process_type` int(11) NOT NULL,
  `synch_up` tinyint(1) NOT NULL,
  `synch_down` tinyint(1) NOT NULL,
  `done_by` int(11) NOT NULL,
  `done_at` datetime NOT NULL,
  PRIMARY KEY (`shop_id`,`station_id`,`pos_date`,`day_process_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for department_itemclass
-- ----------------------------
DROP TABLE IF EXISTS `department_itemclass`;
CREATE TABLE `department_itemclass` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) NOT NULL,
  `itemclass_id` bigint(20) NOT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sales_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `purchase_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cogs_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wages_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_collected_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_paid_account_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for discounts
-- ----------------------------
DROP TABLE IF EXISTS `discounts`;
CREATE TABLE `discounts` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_percentage` tinyint(1) NOT NULL,
  `is_overridable` tinyint(1) NOT NULL,
  `is_item_specific` tinyint(1) NOT NULL,
  `permitted_for` tinyint(1) NOT NULL,
  `is_promotion` tinyint(1) NOT NULL DEFAULT '0',
  `price` decimal(14,5) DEFAULT NULL,
  `account_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `grouping_quantity` int(11) NOT NULL DEFAULT '1',
  `allow_editing` tinyint(1) NOT NULL DEFAULT '0',
  `is_valid` tinyint(1) NOT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `time_from` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time_to` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `week_days` varchar(27) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `disc_password` varchar(40) COLLATE utf8_unicode_ci NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for dr_dtl
-- ----------------------------
DROP TABLE IF EXISTS `dr_dtl`;
CREATE TABLE `dr_dtl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dr_hdr_id` bigint(20) NOT NULL,
  `shift_group_id` bigint(20) DEFAULT NULL,
  `shift_id` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `day_no` int(15) NOT NULL,
  `start_time` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `end_time` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `unpaid_break` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for dr_hdr
-- ----------------------------
DROP TABLE IF EXISTS `dr_hdr`;
CREATE TABLE `dr_hdr` (
  `id` bigint(20) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date DEFAULT NULL,
  `is_active` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'active=1,inactive=0',
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for employee_categories
-- ----------------------------
DROP TABLE IF EXISTS `employee_categories`;
CREATE TABLE `employee_categories` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for employee_shift_group
-- ----------------------------
DROP TABLE IF EXISTS `employee_shift_group`;
CREATE TABLE `employee_shift_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shift_groups_id` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `employee_category_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `sex` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dob` date NOT NULL,
  `f_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `m_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `l_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `doj` date DEFAULT NULL,
  `wage_type` tinyint(4) NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loc_address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loc_country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loc_zip_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loc_phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loc_fax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cost_per_hour` decimal(14,5) DEFAULT NULL,
  `over_time_pay_rate` decimal(14,5) DEFAULT '0.00',
  `work_permit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for esp_config
-- ----------------------------
DROP TABLE IF EXISTS `esp_config`;
CREATE TABLE `esp_config` (
  `id` int(11) NOT NULL,
  `esp_login_url` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `esp_sales_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `esp_sales_return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `esp_search_by_code_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `esp_search_by_branch_url` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `shop_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for flash_messages_hdr
-- ----------------------------
DROP TABLE IF EXISTS `flash_messages_hdr`;
CREATE TABLE `flash_messages_hdr` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `content` text COLLATE utf8_unicode_ci NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `display_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0-> means shop, 1-> POS, 2->Both ',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'Message type. 0 is Custom(mesaage from server). 1= Sync Update Message ',
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'Row delete status. 0 is default. 0= not deleted record. 1=deleted record',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for global_item_attributes
-- ----------------------------
DROP TABLE IF EXISTS `global_item_attributes`;
CREATE TABLE `global_item_attributes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for global_roundings
-- ----------------------------
DROP TABLE IF EXISTS `global_roundings`;
CREATE TABLE `global_roundings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tax` int(11) NOT NULL,
  `bill_total` int(11) NOT NULL,
  `item_total` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  `customer_type_item_price` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for holidays
-- ----------------------------
DROP TABLE IF EXISTS `holidays`;
CREATE TABLE `holidays` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hd_year` bigint(20) NOT NULL,
  `hd_month` bigint(20) NOT NULL,
  `hd_date` bigint(20) NOT NULL,
  `reason` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for hot_items
-- ----------------------------
DROP TABLE IF EXISTS `hot_items`;
CREATE TABLE `hot_items` (
  `id` bigint(20) NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `display_order` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inter_shop_item_transfer_dtl
-- ----------------------------
DROP TABLE IF EXISTS `inter_shop_item_transfer_dtl`;
CREATE TABLE `inter_shop_item_transfer_dtl` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `hdr_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  `rate` decimal(14,5) NOT NULL,
  `dtl_tax_id` int(11) DEFAULT NULL,
  `tax_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_calculation_method` tinyint(2) DEFAULT NULL,
  `dtl_tax1_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax2_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax3_amount` decimal(14,5) DEFAULT NULL,
  `discount_amount` decimal(14,5) DEFAULT NULL,
  `tax_amount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) NOT NULL,
  `expected_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inter_shop_item_transfer_hdr
-- ----------------------------
DROP TABLE IF EXISTS `inter_shop_item_transfer_hdr`;
CREATE TABLE `inter_shop_item_transfer_hdr` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `source_shop_id` bigint(20) DEFAULT NULL,
  `source_shop_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `destination_shop_id` bigint(20) DEFAULT NULL,
  `destination_shop_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transfer_date` date NOT NULL,
  `is_saleitem` tinyint(2) NOT NULL COMMENT ' 0=saleitem transfer,1=stockitem transfer',
  `is_transfer` tinyint(2) NOT NULL COMMENT ' 0=transfered,1=received',
  `hdr_tax_calculation_method` tinyint(2) DEFAULT NULL,
  `other_amount` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `detail_total` decimal(14,5) NOT NULL,
  `total_tax_amount` decimal(14,5) NOT NULL,
  `total_amount` decimal(14,5) NOT NULL,
  `transfer_status` tinyint(2) NOT NULL COMMENT ' 0=Approval Waiting,1=Approved,2=Cacelled',
  `transfered_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `received_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for item_class_display_order
-- ----------------------------
DROP TABLE IF EXISTS `item_class_display_order`;
CREATE TABLE `item_class_display_order` (
  `id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `sub_class_id` bigint(20) NOT NULL,
  `display_order` int(11) DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `item_class_display_order`
ADD INDEX `idx_item_class_display_order_menu_id` (`menu_id`) USING BTREE ,
ADD INDEX `idx_item_class_display_order_sub_class_id` (`sub_class_id`) USING BTREE,
ADD INDEX `idx_item_class_display_order_display_order` (`display_order`) USING BTREE ;

-- ----------------------------
-- Table structure for item_classes
-- ----------------------------
DROP TABLE IF EXISTS `item_classes`;
CREATE TABLE `item_classes`  (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `hsn_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `alternative_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `department_id` bigint(14) NULL DEFAULT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `menu_id` bigint(20) NULL DEFAULT NULL,
  `super_class_id` bigint(20) NULL DEFAULT NULL,
  `tax_calculation_method` tinyint(4) NULL DEFAULT NULL,
  `taxation_based_on` tinyint(1) NULL DEFAULT NULL COMMENT '0 -> Based on Sale Item 1 -> Based on service type + sale item',
  `tax_id` bigint(20) NULL DEFAULT NULL,
  `tax_exemption` smallint(2) NULL DEFAULT 0,
  `tax_group_id` bigint(20) NULL DEFAULT NULL,
  `display_order` tinyint(4) NULL DEFAULT NULL,
  `print_order` int(11) NULL DEFAULT NULL,
  `account_code` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `fg_color` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `bg_color` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `item_thumb` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime(0) NOT NULL,
  `updated_by` int(11) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT 0,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `is_system` tinyint(1) NOT NULL DEFAULT 0,
  `is_synchable` tinyint(1) NOT NULL DEFAULT 1 COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mrp_item_category
-- ----------------------------
DROP TABLE IF EXISTS `mrp_item_category`;
CREATE TABLE `mrp_item_category` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `is_system` tinyint(4) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'publish to que table status.default value is 0,  (0= to publish 1= published successfully, 2= publication failed)',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_item_category_code` (`code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of mrp_item_category
-- ----------------------------


-- ----------------------------
-- Table structure for mrp_profit_category
-- ---------------------------
DROP TABLE IF EXISTS `mrp_profit_category`;
CREATE TABLE `mrp_profit_category` (
  `id` int(11) NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `is_system` tinyint(4) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'publish to que table status.default value is 0,  (0= to publish 1= published successfully, 2= publication failed)',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_profit_category_code` (`code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of mrp_profit_category
-- ----------------------------

-- ----------------------------
-- Table structure for mrp_department
-- ----------------------------
DROP TABLE IF EXISTS `mrp_department`;
CREATE TABLE `mrp_department` (
    `id`  int(11) NOT NULL ,
    `code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
    `name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
    `description`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
    `dept_type`  tinyint(4) NOT NULL DEFAULT 0 ,
    `is_system`  tinyint(4) NOT NULL DEFAULT 0 ,
    `is_deleted`  tinyint(4) NOT NULL DEFAULT 0 ,
    `created_by` int(11) NOT NULL,
    `created_at` datetime NOT NULL,
    `updated_by` int(11) DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'publishe to que table status.default value is 0,  (0= to publish 1= published successfully, 2= publication failed)',
    `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
    `last_sync_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Records of mrp_department
-- ----------------------------

-- ----------------------------
-- Table structure for kitchens
-- ----------------------------
DROP TABLE IF EXISTS `kitchens`;
CREATE TABLE `kitchens` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `printer_name` varchar(250) COLLATE utf8_unicode_ci NULL,
  `printer_port` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for login_sessions
-- ----------------------------
DROP TABLE IF EXISTS `login_sessions`;
CREATE TABLE `login_sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pos_id` int(11) NOT NULL,
  `cashier_shift_id` int(11) NOT NULL,
  `login_user_id` int(11) NOT NULL,
  `start_at` datetime NOT NULL,
  `end_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for menu_departments
-- ----------------------------
DROP TABLE IF EXISTS `menu_departments`;
CREATE TABLE `menu_departments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'publishe to que table status.default value is 0,  (0= to publish 1= published successfully,',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_default_menu` tinyint(1) NOT NULL DEFAULT '0',
  `enable_h1_button` tinyint(1) NOT NULL DEFAULT '0',
  `enable_h2_button` tinyint(1) NOT NULL DEFAULT '0',
  `enable_h3_button` tinyint(1) NOT NULL DEFAULT '0',
  `color` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'publishe to que table status.default value is 0,  (0= to publish 1= published successfully, 2= publication failed)',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_combo_contents
-- ----------------------------
DROP TABLE IF EXISTS `order_combo_contents`;
CREATE TABLE `order_combo_contents` (
  `id` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `combo_content_row_id` int(11) NOT NULL,
  `content_item_id` bigint(20) NOT NULL,
  `qty` decimal(14,5) NOT NULL,
  `price_diff` decimal(14,5) NOT NULL,
  `combo_sale_item_id` bigint(20) NOT NULL,
  `is_substitutable` tinyint(4) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for order_discounts
-- ----------------------------
DROP TABLE IF EXISTS `order_discounts`;
CREATE TABLE `order_discounts` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_payment_hdr_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `discount_id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(14,5) NOT NULL DEFAULT '0.00',
  `is_percentage` tinyint(1) NOT NULL DEFAULT '1',
  `is_overridable` tinyint(1) NOT NULL DEFAULT '0',
  `amount` decimal(14,5) NOT NULL DEFAULT '0.00',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_discounts`
ADD INDEX `idx_order_discounts_order_id` (`order_id`) USING BTREE,
ADD INDEX `idx_order_discounts_discount_id` (`discount_id`) USING BTREE,
ADD INDEX `idx_order_discounts_code` (`code`) USING BTREE;

-- ----------------------------
-- Table structure for order_pre_discounts
-- ----------------------------
DROP TABLE IF EXISTS `order_pre_discounts`;
CREATE TABLE `order_pre_discounts` (

  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `discount_id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(8,2) NOT NULL DEFAULT '0.00',
  `is_percentage` tinyint(1) NOT NULL DEFAULT '1',
  `is_overridable` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_pre_discounts`
ADD INDEX `idx_order_pre_discounts_order_id` (`order_id`) USING BTREE,
ADD INDEX `idx_order_pre_discounts_discount_id` (`discount_id`) USING BTREE,
ADD INDEX `idx_order_pre_discounts_code` (`code`) USING BTREE;

-- ----------------------------
-- Table structure for order_dtls
-- ----------------------------
DROP TABLE IF EXISTS `order_dtls`;
CREATE TABLE `order_dtls` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `sale_item_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `sale_item_hsn_code` varchar(50) COLLATE utf8_unicode_ci NULL,
  `sub_class_id` bigint(20) NOT NULL,
  `sub_class_code` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_hsn_code` varchar(50) COLLATE utf8_unicode_ci NULL,
  `sub_class_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` decimal(14,5) DEFAULT NULL,
  `tray_weight` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tray_code` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uom_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uom_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `uom_symbol` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_calculation_method` tinyint(4) DEFAULT NULL,
  `is_open` tinyint(4) DEFAULT NULL,
  `is_combo_item` tinyint(4) NOT NULL,
  `fixed_price` decimal(14,5) DEFAULT NULL,
  `rtls_price` decimal(14,5) DEFAULT NULL,
  `whls_price` decimal(14,5) DEFAULT NULL,
  `is_whls_price_pc` tinyint(1) DEFAULT '0',
  `customer_price_variance` decimal(14,5) DEFAULT NULL,
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_id_home_service` bigint(20) NULL,
  `tax_id_table_service` bigint(20) NULL,
  `tax_id_take_away_service` bigint(20) null,
  `tax_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_tax1_applied` tinyint(4) DEFAULT NULL,
  `tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax1_pc` decimal(14,5) DEFAULT NULL,
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `is_tax2_applied` tinyint(4) DEFAULT NULL,
  `tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax2_pc` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `is_tax3_applied` tinyint(4) DEFAULT NULL,
  `tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax3_pc` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `is_gst_applied` tinyint(4) DEFAULT NULL,
  `gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_pc` decimal(14,5) DEFAULT NULL,
  `gst_amount` decimal(14,5) DEFAULT NULL,
  `is_tax1_included_in_gst` tinyint(4) DEFAULT NULL,
  `is_tax2_included_in_gst` tinyint(4) DEFAULT NULL,
  `is_tax3_included_in_gst` tinyint(4) DEFAULT NULL,
  `is_sc_included_in_gst` tinyint(4) DEFAULT NULL,
  `sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_sc_applied` tinyint(4) DEFAULT NULL,
  `sc_pc` decimal(14,5) DEFAULT NULL,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `item_total` decimal(14,5) DEFAULT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_id` bigint(20) NOT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(4) DEFAULT NULL,
  `discount_is_overridable` tinyint(4) DEFAULT NULL,
  `discount_is_item_specific` tinyint(4) DEFAULT NULL,
  `discount_permitted_for` tinyint(4) DEFAULT NULL,
  `discount_is_promotion` tinyint(4) DEFAULT '0',
  `discount_amount` decimal(14,5) NOT NULL,
  `discount_variants` decimal(14,5) DEFAULT NULL,
  `discount_grouping_quantity` decimal(14,5) NOT NULL,
  `discount_allow_editing` smallint(6) NOT NULL,
  `round_adjustment` decimal(14,5) DEFAULT NULL,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_void` tinyint(4) DEFAULT '0',
  `cashier_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_time` datetime DEFAULT NULL,
  `serving_table_id` bigint(20) DEFAULT NULL,
  `served_by` bigint(20) DEFAULT NULL,
  `service_type` tinyint(1) DEFAULT NULL,
  `parent_dtl_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `has_choices` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `item_type` int(11) DEFAULT NULL,
  `sale_item_choices_id` int(11) DEFAULT NULL,
  `sale_item_choices_free_items` int(11) DEFAULT NULL,
  `sale_item_choices_max_items` int(11) DEFAULT NULL,
  `sale_item_choices_choice_id` int(11) DEFAULT NULL,
  `sale_item_choices_choice_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sale_item_choices_choice_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sale_item_choices_choice_description` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sale_item_choices_choice_is_global` smallint(1) DEFAULT NULL,
  `combo_content_id` bigint(20) DEFAULT NULL,
  `combo_content_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_content_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_content_description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_content_max_items` int(11) DEFAULT NULL,
  `combo_content_uom_id` bigint(20) DEFAULT NULL,
  `seat_no` int(11) DEFAULT NULL,
  `is_printed_to_kitchen` tinyint(4) DEFAULT '0',
  `kitchen_status`tinyint(1) NULL DEFAULT 0,
  `kitchen_print_status`  varchar(1) NULL, 
  `kitchen_id`  bigint(20) NULL,
  `void_by` bigint(20) DEFAULT NULL,
  `void_at` date DEFAULT NULL,
  `void_time` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Addtional index for sub items
-- ----------------------------
ALTER TABLE `order_dtls`
ADD INDEX `idx_order_dtl_parent_id` (`parent_dtl_id`) USING BTREE,
ADD INDEX `idx_order_dtls_order_id` (`order_id`) USING BTREE ,
ADD INDEX `idx_order_dtls_sale_items_id` (`sale_item_id`) USING BTREE ,
ADD INDEX `idx_order_dtls_item_type` (`item_type`) USING BTREE ;

-- ----------------------------
-- Table structure for order_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `order_hdrs`;
CREATE TABLE `order_hdrs` (
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `order_no` int(11) DEFAULT NULL,
  `invoice_prefix` varchar(50) DEFAULT NULL,
  `invoice_no` int(11) DEFAULT NULL ,
  `shop_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `station_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL COMMENT 'POS open date',
  `order_time` datetime DEFAULT NULL COMMENT 'Current system date and time ',
  `shift_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `is_ar_customer` tinyint(1) DEFAULT NULL,
  `detail_total` decimal(14,5) DEFAULT NULL,
  `total_tax1` decimal(14,5) DEFAULT NULL,
  `total_tax2` decimal(14,5) DEFAULT NULL,
  `total_tax3` decimal(14,5) DEFAULT NULL,
  `total_gst` decimal(14,5) DEFAULT NULL,
  `total_sc` decimal(14,5) DEFAULT NULL,
  `total_detail_discount` decimal(14,5) NOT NULL DEFAULT '0.00',
  `final_round_amount` decimal(14,5) DEFAULT NULL,
  `total_amount` decimal(14,5) DEFAULT NULL,
  `total_amount_paid` decimal(14,5) NOT NULL DEFAULT '0.00',
  `total_balance` decimal(14,5) NOT NULL DEFAULT '0.00',
  `actual_balance_paid` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_out` decimal(14,5) DEFAULT '0.00',
  `remarks` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `closing_date` date DEFAULT NULL COMMENT 'POS closing date',
  `closing_time` datetime DEFAULT NULL COMMENT 'Current system date and time',
  `closed_by` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `void_by` bigint (20) DEFAULT NULL,
  `void_at` datetime DEFAULT NULL,
  `total_print_count` tinyint(4) DEFAULT NULL,
  `refund_total_tax1` decimal(14,5) DEFAULT '0.00',
  `refund_total_tax2` decimal(14,5) DEFAULT '0.00',
  `refund_total_tax3` decimal(14,5) DEFAULT '0.00',
  `refund_total_gst` decimal(14,5) DEFAULT '0.00',
  `refund_total_sc` decimal(14,5) DEFAULT '0.00',
  `refund_amount` decimal(14,5) DEFAULT '0.00',
  `due_datetime` datetime DEFAULT NULL,
  `order_medium` tinyint(4) DEFAULT NULL,
  `delivery_type` tinyint(1) DEFAULT NULL,
  `extra_charges` decimal(8,2) DEFAULT NULL,
  `extra_charge_tax_id` bigint(20) DEFAULT NULL,
  `extra_charge_tax_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax1_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax1_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax2_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax2_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax3_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax3_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_sc_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_sc_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_gst_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_gst_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_remarks` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_message` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL,
  `bill_less_tax_amount` decimal(14,5) NOT NULL DEFAULT '0.00',
  `bill_discount_amount` decimal(14,5) NOT NULL DEFAULT '0.00',
  `bill_discount_percentage` decimal(8,2) NOT NULL DEFAULT '0.00',
  `serving_table_id` bigint(20) DEFAULT NULL,
  `served_by` bigint(20) DEFAULT NULL,
  `service_type` tinyint(1) DEFAULT NULL,
  `covers` int(11) DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_process_status` tinyint(4) DEFAULT '0',
  `is_locked` tinyint(1) DEFAULT '0',
  `locked_station_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `locked_station_id` bigint(20) DEFAULT NULL,
  `alias_text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_required_kitchen_print` tinyint(1) DEFAULT '0',
  `is_required_bill_print` tinyint(1) DEFAULT '0',
  `driver_name` varchar(255) COLLATE utf8_unicode_ci  NULL,
  `vehicle_number` varchar(255) COLLATE utf8_unicode_ci  NULL,
  `sync_ext_db_status`  tinyint(4) NULL DEFAULT 0,
  `sync_ext_db_msg`  varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Index by locked_station_code
-- ----------------------------
ALTER TABLE `order_hdrs`
ADD INDEX `idx_order_hdrs_locked_station_code` (`locked_station_code`) USING BTREE ,
ADD INDEX `idx_order_hdrs_invoice_no` (`invoice_no`) USING BTREE ,
ADD INDEX `idx_order_hdrs_order_id` (`order_id`) USING BTREE ,
ADD INDEX `idx_order_hdrs_status` (`status`) USING BTREE ;

-- ----------------------------
-- Table structure for order_customers
-- ----------------------------
DROP TABLE IF EXISTS `order_customers`;
CREATE TABLE `order_customers` (
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `code` varchar(10) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address2` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address3` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address4` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tin` varchar(2255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_reg_type`  bigint(20) DEFAULT 2,
  `gst_party_type`  bigint(20) DEFAULT 1,
  `is_ar` tinyint(1) NOT NULL DEFAULT 0,
  `customer_type` int(11) NULL DEFAULT 0,
  `remarks`  TEXT,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for gst_reg_type
-- ----------------------------
DROP TABLE IF EXISTS `gst_reg_type`;
CREATE TABLE `gst_reg_type` (
`id`  bigint(20) NOT NULL ,
`name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `gst_reg_type` VALUES(1,'Regular');
INSERT INTO `gst_reg_type` VALUES(2,'Consumer');
INSERT INTO `gst_reg_type` VALUES(3,'Composition');

-- ----------------------------
-- Table structure for gst_party_type
-- ----------------------------
DROP TABLE IF EXISTS `gst_party_type`;
CREATE TABLE `gst_party_type` (
`id`  bigint(20) NOT NULL ,
`name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `gst_party_type` VALUES(1,'None');
INSERT INTO `gst_party_type` VALUES(2,'Deemed Export');
INSERT INTO `gst_party_type` VALUES(3,'Embassy/UN Body');
INSERT INTO `gst_party_type` VALUES(4,'Government Entity');
INSERT INTO `gst_party_type` VALUES(5,'SEZ');

-- ----------------------------
-- Table structure for order_params
-- ----------------------------
DROP TABLE IF EXISTS `order_params`;
CREATE TABLE `order_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_iterm_attribs_supported` tinyint(1) NOT NULL,
  `is_serial_no_visible` tinyint(1) NOT NULL,
  `is_item_code_visible` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_payments
-- ----------------------------
DROP TABLE IF EXISTS `order_payments`;
CREATE TABLE `order_payments` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_payment_hdr_id` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_mode` tinyint(2) DEFAULT '1' COMMENT 'Cash(0,"Pay By Cash"),Card(1,"Pay By Card"),Coupon(2,"Pay By Voucher"), Company(3,"Pay By Company"),Discount(4,"Discount"),Balance(5,"Balance"),Repay(6,"Re-Payment"),Cash10(100,"Pay By Cash 10"),Cash20(101,"Pay By Cash 20");',
  `paid_amount` decimal(14,5) DEFAULT NULL,
  `card_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_no` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_on_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_expiry_month` bigint(20) DEFAULT NULL,
  `card_expiry_year` decimal(4,0) DEFAULT NULL,
  `card_approval_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_account_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pos_customer_receipt` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pos_merchant_receipt` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  `voucher_value` decimal(14,5) DEFAULT NULL,
  `voucher_count` tinyint(2) DEFAULT NULL,
  `cashier_id` bigint(20) DEFAULT NULL,
  `cashier_shift_id` bigint(20) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(2) DEFAULT NULL,
  `discount_is_overridable` tinyint(2) DEFAULT NULL,
  `discount_amount` decimal(14,5) DEFAULT NULL,
  `is_repayment` tinyint(1) NOT NULL DEFAULT '0',
  `is_voucher_balance_returned` tinyint(4) DEFAULT '0',
  `partial_balance` decimal(14,5) DEFAULT '0.00',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT now(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT now(),
  `pms_sync_status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_payments`
ADD INDEX `idx_order_payments_order_id` (`order_id`) USING BTREE ;
 
-- ----------------------------
-- Table structure for order_payment_hdr
-- ----------------------------
DROP TABLE IF EXISTS `order_payment_hdr`;
CREATE TABLE `order_payment_hdr` (
  `id`  varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_date`  date DEFAULT NULL,
  `payment_time`  datetime DEFAULT NULL,
  `shift_id`  bigint(20) DEFAULT NULL,
  `detail_total`  decimal(14,5) DEFAULT NULL,
  `total_tax1`  decimal(14,5) DEFAULT NULL,
  `total_tax2`  decimal(14,5) DEFAULT NULL,
  `total_tax3`  decimal(14,5) DEFAULT NULL,
  `total_gst`  decimal(14,5) DEFAULT NULL,
  `total_sc`  decimal(14,5) DEFAULT NULL,
  `total_detail_discount`  decimal(14,5) NOT NULL DEFAULT '0.00',
  `final_round_amount`  decimal(14,5) DEFAULT NULL,
  `total_amount`  decimal(14,5) DEFAULT NULL,
  `total_amount_paid`  decimal(14,5) NOT NULL DEFAULT '0.00',
  `total_balance`  decimal(14,5) NOT NULL DEFAULT '0.00',
  `actual_balance_paid`  decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_out`  decimal(14,5) DEFAULT '0.00',
  `bill_less_tax_amount`  decimal(14,5) NOT NULL DEFAULT '0.00',
  `bill_discount_amount`  decimal(14,5) NOT NULL DEFAULT '0.00',
  `is_refund`  tinyint(1) NOT NULL DEFAULT '0',
  `is_advance` tinyint(1) DEFAULT NULL,
  `is_final` tinyint(1) NOT NULL DEFAULT '0',
  `remarks`  varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by`  int(11) DEFAULT NULL,
  `created_at`  datetime DEFAULT NULL,
  `updated_by`  int(11) DEFAULT NULL,
  `updated_at`  datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_payment_hdr`
ADD INDEX `idx_order_payment_hdr_order_id` (`order_id`) USING BTREE ;

-- ----------------------------
-- Table structure for order_refunds
-- ----------------------------
DROP TABLE IF EXISTS `order_refunds`;
CREATE TABLE `order_refunds` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
    `order_dtl_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
    `order_payment_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
    `qty` decimal(14,5) DEFAULT NULL,
    `paid_amount` decimal(14,5) DEFAULT NULL COMMENT 'Tax Included',
    `tax1_amount` decimal(14,5) DEFAULT NULL,
    `tax2_amount` decimal(14,5) DEFAULT NULL,
    `tax3_amount` decimal(14,5) DEFAULT NULL,
    `sc_amount` decimal(14,5) DEFAULT NULL,
    `gst_amount` decimal(14,5) DEFAULT NULL,
    `refund_date` date DEFAULT NULL ,
    `refund_time`  datetime DEFAULT NULL,
    `refunded_by` int(11) DEFAULT NULL,
    `sync_message`  longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
    `sync_status`  tinyint(4) NOT NULL DEFAULT 0,
    `created_by`  int(11) NULL DEFAULT NULL,
    `created_at`  datetime NULL DEFAULT NULL,
    `updated_by`  int(11) NULL DEFAULT NULL,
    `updated_at`  datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_refunds`
ADD INDEX `idx_order_refunds_order_id` (`order_id`) USING BTREE,
ADD INDEX `idx_order_refunds_order_dtl_id` (`order_dtl_id`) USING BTREE,
ADD INDEX `idx_order_refunds_order_payment_id` (`order_payment_id`) USING BTREE;

-- ----------------------------
-- Table structure for order_split_dtls
-- ----------------------------
DROP TABLE IF EXISTS `order_split_dtls`;
CREATE TABLE `order_split_dtls` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `split_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_sub_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_qty` decimal(14,5) DEFAULT NULL,
  `order_dtl_price` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_split_dtls`
ADD INDEX `idx_order_split_dtls_order_id` (`order_id`) USING BTREE ,
ADD INDEX `idx_order_split_dtls_order_dtl_id` (`order_dtl_id`) USING BTREE ,
ADD INDEX `idx_order_split_dtls_split_id` (`split_id`) USING BTREE ;
	
-- ----------------------------
-- Table structure for order_splits
-- ----------------------------
DROP TABLE IF EXISTS `order_splits`;
CREATE TABLE `order_splits` (
	`id` VARCHAR (60) COLLATE utf8_unicode_ci NOT NULL,
	`order_id` VARCHAR (50) COLLATE utf8_unicode_ci NOT NULL,
	`split_no` INT (11) DEFAULT NULL,
	`based_on` TINYINT (4) NOT NULL DEFAULT 0,
	`value` decimal(14,5) DEFAULT NULL,
	`description` VARCHAR (250) COLLATE utf8_unicode_ci DEFAULT NULL,
	`amount` decimal(14,5) DEFAULT NULL,
	`adj_amount` decimal(14,5) DEFAULT NULL,
	`actual_amount_paid` decimal(14,5) DEFAULT NULL,
	`discount` decimal(14,5) DEFAULT NULL,
	`round_adj` decimal(14,5) DEFAULT NULL,
	`part_pay_adj` decimal(14,5) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci;

ALTER TABLE `order_splits` 
ADD INDEX `idx_order_splits_order_id` (`order_id`) USING BTREE ;

-- ----------------------------
-- Table structure for order_queue
-- ----------------------------
DROP TABLE IF EXISTS order_queue;
CREATE TABLE `order_queue` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`order_id` VARCHAR (50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci  ;

ALTER TABLE `order_queue`
ADD INDEX `idx_order_queue_order_id` (`order_id`) USING BTREE;

-- ----------------------------
-- Table structure for pos_invoice
-- ----------------------------
DROP TABLE IF EXISTS pos_invoice;
CREATE TABLE `pos_invoice` (
      `id` BIGINT (20) NOT NULL AUTO_INCREMENT,
      `order_id` VARCHAR (50) CHARACTER
      SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
      PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci;

ALTER TABLE `pos_invoice` 
ADD INDEX `idx_pos_invoice_order_id` (`order_id`) USING BTREE ;

-- ----------------------------
-- Table structure for order_serving_seats
-- ----------------------------
DROP TABLE IF EXISTS `order_serving_seats`;
CREATE TABLE `order_serving_seats` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `table_id` int(11) NOT NULL,
  `seat_no` int(11) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_void` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_serving_seats` 
ADD INDEX `idx_order_serving_seats_order_id` (`order_id`) USING BTREE ,
ADD INDEX `idx_order_serving_seats_table_id` (`table_id`) USING BTREE ;

-- ----------------------------
-- Table structure for order_serving_tables
-- ----------------------------
DROP TABLE IF EXISTS `order_serving_tables`;
CREATE TABLE `order_serving_tables` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `table_id` bigint(20) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `selected_seat_no` int(11) DEFAULT NULL,
  `is_selected` tinyint(1) DEFAULT '1',
  `is_void` int(11) DEFAULT '0',
  `covers` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `order_serving_tables` 
ADD INDEX `idx_order_serving_tables_order_id` (`order_id`) USING BTREE ,
ADD INDEX `idx_order_serving_tables_table_id` (`table_id`) USING BTREE ;

-- ----------------------------
-- Table structure for order_kitchen_queue
-- ----------------------------
DROP TABLE IF EXISTS `order_kitchen_queue`;
CREATE TABLE `order_kitchen_queue` (
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `kitchen_id` bigint(20) NOT NULL,
  `kitchen_queue_no` bigint(20) NOT NULL,
  `printed_time` datetime DEFAULT NULL,
  PRIMARY KEY (`kitchen_queue_no`,`kitchen_id`),
  KEY `idx_order_kitchen_queue_kitchen_id` (`kitchen_id`) USING BTREE,
  KEY `idx_order_kitchen_queue_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_kitchen_queue_kitchen_id_queue_no` (`kitchen_id`,`kitchen_queue_no`) USING BTREE
) ;

-- ----------------------------
-- Table structure for serving_table_images
-- ----------------------------
DROP TABLE IF EXISTS `serving_table_images`;
CREATE TABLE `serving_table_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` text COLLATE utf8_unicode_ci,
  `height` int(11) NOT NULL,
  `width` int(11) NOT NULL,
  `is_system` tinyint(1) DEFAULT '0',
  `is_default` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




-- ----------------------------
-- Table structure for packages
-- ----------------------------
DROP TABLE IF EXISTS `packages`;
CREATE TABLE `packages` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uom_id` bigint(20) NOT NULL,
  `count` decimal(14,4) NOT NULL DEFAULT '0.0000',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for payment_modes
-- ----------------------------
DROP TABLE IF EXISTS `payment_modes`;
CREATE TABLE `payment_modes` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `can_pay_by_cash` tinyint(1) NOT NULL,
  `title_cash` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Cash',
  `can_pay_by_company` tinyint(1) NOT NULL,
  `title_company` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Credit ',
  `can_pay_by_vouchers` tinyint(1) NOT NULL,
  `title_voucher` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Voucher',
  `max_voucher_type` tinyint(4) NOT NULL DEFAULT '0',
  `can_pay_by_card` tinyint(1) NOT NULL DEFAULT '0',
  `title_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Card',
  `can_pay_online` tinyint(4) NOT NULL DEFAULT '1',
  `title_online` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Online',
  `can_cash_refundable` tinyint(1) DEFAULT NULL,
  `can_card_refundable` tinyint(1) DEFAULT NULL,
  `can_company_refundable` tinyint(1) DEFAULT NULL,
  `can_voucher_refundable` tinyint(1) DEFAULT NULL,
  `can_online_refund` tinyint(1) DEFAULT '1',
  `cash_account_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cash_refund_account_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_refund_method` tinyint(4) DEFAULT NULL,
  `can_cash_round` tinyint(1) DEFAULT '1',
  `can_company_round` tinyint(1) DEFAULT '0',
  `can_voucher_round` tinyint(1) DEFAULT '0',
  `can_card_round` tinyint(1) DEFAULT '0',
  `can_online_round` tinyint(1) DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system`  tinyint(1) NOT NULL DEFAULT 0,
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for pos_scanner_patterns
-- ----------------------------
DROP TABLE IF EXISTS `pos_scanner_patterns`;
CREATE TABLE `pos_scanner_patterns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` smallint(2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `char_prefix`	varchar(1) DEFAULT NULL,
  `char_suffix`	varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for pos_system_functions
-- ----------------------------
DROP TABLE IF EXISTS `pos_system_functions`;
CREATE TABLE `pos_system_functions` (
  `id` bigint(20) NOT NULL,
  `code` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_view_applicable` tinyint(1) DEFAULT NULL,
  `is_add_applicable` tinyint(1) DEFAULT NULL,
  `is_edit_applicable` tinyint(1) DEFAULT NULL,
  `is_delete_applicable` tinyint(1) DEFAULT NULL,
  `is_execute_applicable` tinyint(1) DEFAULT NULL,
  `is_publish_applicable` tinyint(4) NOT NULL DEFAULT '0',
  `is_export_applicable` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_sync_to_pos` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If is_sync_to_pos is 1 Then record is send to pos so no need to insert sync_que table, if 0  then show check box in page and when checked insert recods o sync table',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for pos_user_group_functions
-- ----------------------------
DROP TABLE IF EXISTS `pos_user_group_functions`;
CREATE TABLE `pos_user_group_functions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_group_id` bigint(20) NOT NULL,
  `pos_system_function_id` bigint(20) NOT NULL,
  `can_view` tinyint(1) DEFAULT NULL,
  `can_add` tinyint(1) DEFAULT NULL,
  `can_edit` tinyint(1) DEFAULT NULL,
  `can_delete` tinyint(1) DEFAULT NULL,
  `can_execute` tinyint(1) DEFAULT NULL,
  `can_publish` tinyint(1) DEFAULT NULL,
  `can_export` tinyint(1) DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for reasons
-- ----------------------------
DROP TABLE IF EXISTS `reasons`;
CREATE TABLE `reasons` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `context` tinyint(4) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for report_tags
-- ----------------------------
DROP TABLE IF EXISTS `report_tags`;
CREATE TABLE `report_tags` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for rounding
-- ----------------------------
DROP TABLE IF EXISTS `rounding`;
CREATE TABLE `rounding` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `round_to` decimal(14,5) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_2` (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for sale_item_choices
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_choices`;
CREATE TABLE `sale_item_choices` (
  `id` bigint(20) NOT NULL,
  `sale_item_id` int(11) NOT NULL,
  `choice_id` int(11) NOT NULL,
  `free_items` decimal(14,5) NOT NULL DEFAULT '0',
  `max_items` decimal(14,5) NOT NULL DEFAULT '-1',
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` smallint(1) NOT NULL DEFAULT '0',
  `is_deleted` smallint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` smallint(1) NOT NULL DEFAULT '1',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `sale_item_choices`
ADD INDEX `idx_sale_item_choices_sale_item_id` (`sale_item_id`) USING BTREE ,
ADD INDEX `idx_sale_item_choices_choice_id` (`choice_id`) USING BTREE ;

-- ----------------------------
-- Table structure for sale_item_combo_contents
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_combo_contents`;
CREATE TABLE `sale_item_combo_contents` (
  `id` bigint(20) NOT NULL,
  `combo_sale_item_id` bigint(20) NOT NULL,
  `combo_content_item_id` bigint(20) NOT NULL,
  `max_items` int(11) DEFAULT '1',
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `sale_item_combo_contents`
ADD INDEX `idx_sale_item_combo_contents_combo_sale_item_id` (`combo_sale_item_id`) USING BTREE ,
ADD INDEX `idx_sale_item_combo_contents_combo_content_item_id` (`combo_content_item_id`) USING BTREE ;

-- ----------------------------
-- Table structure for sale_item_combo_substitutions
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_combo_substitutions`;
CREATE TABLE `sale_item_combo_substitutions` (
  `id` bigint(20) NOT NULL,
  `sale_item_combo_content_id` bigint(20) DEFAULT NULL,
  `substitution_sale_item_id` bigint(20) DEFAULT NULL,
  `quantity` decimal(14,5) DEFAULT NULL,
  `price_difference` float(10,2) DEFAULT NULL,
  `is_default` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `sale_item_combo_substitutions`
ADD INDEX `idx_sale_item_combo_substitutions_sale_item_combo_content_id` (`sale_item_combo_content_id`) USING BTREE ,
ADD INDEX `idx_sale_item_combo_substitutions_substitution_sale_item_id` (`substitution_sale_item_id`) USING BTREE ;

-- ----------------------------
-- Table structure for sale_item_discounts
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_discounts`;
CREATE TABLE `sale_item_discounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `discount_id` bigint(20) NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `price` decimal(14,5) DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `sale_item_discounts`
ADD INDEX `idx_sale_item_discounts_discount_id` (`discount_id`) USING BTREE ,
ADD INDEX `idx_sale_item_discounts_sale_item_id` (`sale_item_id`) USING BTREE ;

-- ----------------------------
-- Table structure for sale_item_display_order
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_display_order`;
CREATE TABLE `sale_item_display_order` (
  `id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `sub_class_id` bigint(20) NOT NULL,
  `sale_item_id` bigint(20) DEFAULT NULL,
  `display_order` int(11) DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `sale_item_display_order`
ADD INDEX `idx_sale_item_display_order_menu_id` (`menu_id`) USING BTREE ,
ADD INDEX `idx_sale_item_display_order_sub_class_id` (`sub_class_id`) USING BTREE,
ADD INDEX `idx_sale_item_display_order_sale_item_id` (`sale_item_id`) USING BTREE, 
ADD INDEX `idx_sale_item_display_order_display_order` (`display_order`) USING BTREE ;

-- ----------------------------
-- Table structure for sale_item_images
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_images`;
CREATE TABLE `sale_item_images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sale_item_id` int(11) NOT NULL,
  `file_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `alter_text` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(1) NOT NULL,
  `display_order` int(11) NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for sale_items
-- ----------------------------
DROP TABLE IF EXISTS `sale_items`;
CREATE TABLE `sale_items` (
  `id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) DEFAULT NULL,
  `stock_item_code` varchar(20) COLLATE utf8_unicode_ci NULL,
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `hsn_code` varchar(50) COLLATE utf8_unicode_ci NULL,
  `is_group_item` tinyint(1) NOT NULL DEFAULT '0',
  `group_item_id` bigint(20) DEFAULT NULL,
  `is_combo_item` tinyint(4) NOT NULL DEFAULT '0',
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sub_class_id` bigint(20) DEFAULT NULL,
  `is_valid` tinyint(1) DEFAULT '1',
  `is_manufactured` tinyint(4) DEFAULT '0',
  `alternative_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_printable_to_kitchen` tinyint(1) NOT NULL DEFAULT '1',
  `name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `barcode` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_open` tinyint(4) NOT NULL DEFAULT '0',
  `qty_on_hand` decimal(14,5) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `item_weight` decimal(14,5) DEFAULT 1,
  `profit_category_id` bigint(20) DEFAULT NULL,
  `prd_department_id` bigint(20) NULL DEFAULT NULL,
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_exemption` smallint(2) DEFAULT 0,
  `tax_group_id` bigint(20) DEFAULT NULL,
  `tax_id_home_service` bigint(20) NULL,
  `tax_id_table_service` bigint(20) NULL,
  `tax_id_take_away_service` bigint(20) NULL,
  `fixed_price` decimal(14,5) DEFAULT NULL,
  `whls_price`  decimal(14,5) DEFAULT NULL,
  `is_whls_price_pc` tinyint(1) NOT NULL DEFAULT '0',
  `item_cost` decimal(14,5) NOT NULL DEFAULT '0.00',
  `tax_calculation_method` tinyint(4) DEFAULT 0,
  `taxation_based_on` tinyint(1) DEFAULT '0' COMMENT '0 -> Based on Sale Item 1 -> Based on service type + sale item',
  `is_require_weighing` tinyint(1) DEFAULT '0',
  `display_order` int(11) DEFAULT '0',
  `best_before` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_hot_item_1` tinyint(1) NOT NULL DEFAULT '0',
  `hot_item_1_display_order` smallint(5) DEFAULT NULL,
  `is_hot_item_2` tinyint(1) NOT NULL DEFAULT '0',
  `hot_item_2_display_order` smallint(5) DEFAULT NULL,
  `is_hot_item_3` tinyint(1) NOT NULL DEFAULT '0',
  `hot_item_3_display_order` smallint(5) DEFAULT NULL,
  `fg_color` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bg_color` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tag1` bigint(20) DEFAULT NULL COMMENT 'report_tags.id',
  `tag2` bigint(20) DEFAULT NULL COMMENT 'report_tags.id',
  `tag3` bigint(20) DEFAULT NULL COMMENT 'report_tags.id',
  `tag4` bigint(20) DEFAULT NULL COMMENT 'report_tags.id',
  `tag5` bigint(20) DEFAULT NULL COMMENT 'report_tags.id',
  `choice_ids` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `item_thumb` text COLLATE utf8_unicode_ci,
  `sys_sale_flag`  tinyint(1) NOT NULL DEFAULT '1',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `sale_items` 
ADD INDEX `idx_sale_items_code` (`code`) USING BTREE ,
ADD INDEX `idx_sale_items_id` (`id`) USING BTREE ,
ADD INDEX `idx_sale_items_sub_class_id` (`sub_class_id`) USING BTREE ;

-- ----------------------------
-- Table structure for sale_item_ext
-- ----------------------------
DROP TABLE IF EXISTS `sale_item_ext`;
CREATE TABLE `sale_item_ext` (
  `id` bigint(20) NOT NULL,
  `kitchen_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for sales_amount_target
-- ----------------------------
DROP TABLE IF EXISTS `sales_amount_target`;
CREATE TABLE `sales_amount_target` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) DEFAULT NULL,
  `defined_weekly_target` decimal(14,5) NOT NULL DEFAULT '0.00',
  `last_calculated_date` date DEFAULT NULL,
  `current_day_target_amount` decimal(14,5) DEFAULT NULL,
  `weekly_amount` decimal(20,5) DEFAULT NULL COMMENT 'weekly_amount +current_day_target_amount',
  `monthly_amount` decimal(20,5) DEFAULT NULL COMMENT 'monthly_amount +current_day_target_amount',
  `yearly_amount` decimal(20,5) DEFAULT NULL COMMENT 'yearly_amount +current_day_target_amount',
  `lastyear_sameday_target_amount` decimal(20,5) DEFAULT NULL COMMENT 'last day sale amount.this field is not using now',
  `lastyear_weekly_amount` decimal(20,5) DEFAULT NULL,
  `lastyear_yearly_amount` decimal(20,5) DEFAULT NULL,
  `item_sold_today` decimal(14,5) DEFAULT '0.00',
  `item_sold_this_week` decimal(14,5) DEFAULT '0.00',
  `item_sold_lastyear_this_week` decimal(14,5) DEFAULT '0.00',
  `order_trans_today` decimal(14,5) DEFAULT '0.00',
  `order_trans_this_week` decimal(14,5) DEFAULT '0.00',
  `order_trans_lastyear_this_week` decimal(14,5) DEFAULT '0.00',
  `staff_hrs_today` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_this_week` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_target` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_lastyear_this_week` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_yearly` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_lastyear_yearly` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `labour_cost_today` decimal(14,5) DEFAULT '0.00',
  `labour_cost_this_week` decimal(14,5) DEFAULT '0.00',
  `labour_cost_target` decimal(14,5) DEFAULT '0.00',
  `labour_cost_lastyear_this_week` decimal(14,5) DEFAULT '0.00',
  `labour_cost_yearly_amount` decimal(14,5) DEFAULT '0.00',
  `labour_cost_lastyear_yearly_amount` decimal(14,5) DEFAULT '0.00',
  `is_one_year_above_shop` tinyint(1) NOT NULL DEFAULT '0',
  `current_day_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `current_day_total_extras` DECIMAL(14,5) NOT NULL DEFAULT '0',
  `weekly_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `monthly_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `yearly_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `lastyear_sameday_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `lastyear_weekly_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `lastyear_yearly_total_tax` decimal(14,5) NOT NULL DEFAULT '0.00',
  `current_day_total_detail_discount`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `current_day_totalbill_discount_amt`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `current_day_total_refundamt`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `current_day_total_bill_discount_tax`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `current_day_total_refund_tax`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `current_day_total_extra_charge_tax`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for sales_summary_table
-- ----------------------------
DROP TABLE IF EXISTS `sales_summary_table`;
CREATE TABLE `sales_summary_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `week_no` tinyint(2) NOT NULL,
  `summary_date` date NOT NULL,
  `shop_id` bigint(20) NOT NULL,
  `shop_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `area_codes_id` bigint(20) NOT NULL,
  `area_codes_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `department_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `report_department_id` bigint(20) NOT NULL,
  `report_department_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `super_class_id` bigint(20) NOT NULL,
  `super_class_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_id` bigint(20) NOT NULL,
  `sub_class_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `sale_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sale_item_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fixed_price` decimal(14,5) DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `uom_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `uom_symbol` varchar(20) COLLATE utf8_unicode_ci NULL,
  `total_amount` decimal(14,5) NOT NULL,
  `total_tax` decimal(14,5) NOT NULL,
  `total_discount` decimal(14,5) NOT NULL,
  `item_cost`  decimal(14,5) NOT NULL DEFAULT 0.00000,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1-> closed, 0-> not closed',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for serving_table_location
-- ----------------------------
DROP TABLE IF EXISTS `serving_table_location`;
CREATE TABLE `serving_table_location` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(251) COLLATE utf8_unicode_ci DEFAULT NULL,
  `num_rows` int(11) NOT NULL DEFAULT 0 ,
  `num_columns` int(11) NOT NULL DEFAULT 0 ,
  `apply_service_charge` TINYINT (1) DEFAULT 0,
  `sc_based_on` TINYINT (1) DEFAULT 1,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `is_sc_percentage` TINYINT (1) DEFAULT 1,
  `apply_service_tax` TINYINT (1) DEFAULT 0,
  `service_tax_id` bigint(20) NULL,
  `que_no_prefix` varchar(5) COLLATE utf8_unicode_ci NULL,
  `is_auto_layout` tinyint(1) DEFAULT '1',
  `bg_image` longtext NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `serving_table_location` 
ADD INDEX `idx_serving_table_location_code` (`code`) USING BTREE;

-- ----------------------------
-- Table structure for serving_tables
-- ----------------------------
DROP TABLE IF EXISTS `serving_tables`;
CREATE TABLE `serving_tables` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(251) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1=valid,0=invalid',
  `covers` bigint(20) NOT NULL,
  `row_position` int(11) DEFAULT NULL,
  `column_position` int(11) DEFAULT NULL,
  `serving_table_location_id` int(11) NOT NULL,
  `layout_image` int(11) DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0=Occupied,1=No occupied,2=resolved',
  `created_by` bigint(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0=tables, 1=take away/home delivery service',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `serving_tables` 
ADD INDEX `idx_serving_tables_code` (`code`) USING BTREE;

-- ----------------------------
-- Table structure for shift_groups
-- ----------------------------
DROP TABLE IF EXISTS `shift_groups`;
CREATE TABLE `shift_groups` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shift_summary
-- ----------------------------
DROP TABLE IF EXISTS `shift_summary`;
CREATE TABLE `shift_summary` (
  `auto_id` int(11) NOT NULL AUTO_INCREMENT,
  `shift_id` int(11) NOT NULL,
  `shift_by` int(11) NOT NULL,
  `station_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `opening_date` date NOT NULL COMMENT 'pos open  date',
  `opening_time` datetime NOT NULL,
  `closing_date` datetime NOT NULL,
  `closing_time` datetime NOT NULL,
  `opening_float` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_receipts` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `card_receipts` decimal(14,5) NOT NULL DEFAULT '0.00',
  `card_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `voucher_receipts` decimal(14,5) NOT NULL DEFAULT '0.00',
  `voucher_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `online_receipts` decimal(8,2) NOT NULL DEFAULT '0.00',
  `online_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `accounts_receivable` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_returned` decimal(14,5) NOT NULL DEFAULT '0.00',
  `voucher_balance` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_out` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_refund` decimal(14,5) DEFAULT '0.00',
  `card_refund` decimal(14,5) DEFAULT '0.00',
  `voucher_refund` decimal(14,5) DEFAULT '0.00',
  `online_refund` decimal(14,5) DEFAULT '0.00',
  `accounts_refund` decimal(14,5) DEFAULT '0.00',
  `total_refund` decimal(14,5) DEFAULT '0.00',
  `sales` decimal(14,5) NOT NULL DEFAULT '0.00',
  `previous_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `daily_cashout` decimal(14,5) NOT NULL DEFAULT '0.00',
  `net_cash_received` decimal(14,5) NOT NULL DEFAULT '0.00',
  `voucher_balance_returned` decimal(14,5) NOT NULL DEFAULT '0.00',
  `closing_cash` decimal(14,5) NOT NULL DEFAULT '0.00',
  `actual_cash` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_variance` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_deposit` decimal(14,5) NOT NULL DEFAULT '0.00',
  `cash_remaining` decimal(14,5) NOT NULL DEFAULT '0.00',
  `referance_number` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_status` tinyint(2) NOT NULL DEFAULT '0',
  `sync_message` longtext COLLATE utf8_unicode_ci ,
  PRIMARY KEY (`auto_id`,`station_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shipments
-- ----------------------------
DROP TABLE IF EXISTS `shipments`;
CREATE TABLE `shipments` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area_id` bigint(20) NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_license_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_tax_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cst_no`  varchar(255) COLLATE utf8_unicode_ci NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_subscribe` tinyint(1) DEFAULT 0 ,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_type` tinyint(4) DEFAULT '0',
  `business_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0->Sale ,1-> Production',
  `bank_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_branch` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_ifsc_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_micr_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_account_no` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_ready_to_publish` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 -> means is not ready fro publish ,1-> is ready for publish to  shop',
  `is_initially_synced` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-> means not pplied for initial sync 1-> is initially synced to shop ',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_sync_from_server`  datetime NULL,
  `last_sync_to_server`  datetime NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_dashboard_recods
-- ----------------------------
DROP TABLE IF EXISTS `shop_dashboard_recods`;
CREATE TABLE `shop_dashboard_recods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_sold_today` decimal(14,5) DEFAULT '0.00',
  `item_sold_this_week` decimal(14,5) DEFAULT '0.00',
  `item_sold_lastyear_this_week` decimal(14,5) DEFAULT '0.00',
  `order_trans_today` decimal(14,5) DEFAULT '0.00',
  `order_trans_this_week` decimal(14,5) DEFAULT '0.00',
  `order_trans_lastyear_this_week` decimal(14,5) DEFAULT '0.00',
  `staff_hrs_today` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_this_week` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_target` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_lastyear_this_week` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_yearly` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `staff_hrs_lastyear_yearly` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0:00',
  `labour_cost_today` decimal(14,5) DEFAULT '0.00',
  `labour_cost_this_week` decimal(14,5) DEFAULT '0.00',
  `labour_cost_target` decimal(14,5) DEFAULT '0.00',
  `labour_cost_lastyear_this_week` decimal(14,5) DEFAULT '0.00',
  `labour_cost_yearly_amount` decimal(14,5) DEFAULT '0.00',
  `labour_cost_lastyear_yearly_amount` decimal(14,5) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_departments
-- ----------------------------
DROP TABLE IF EXISTS `shop_departments`;
CREATE TABLE `shop_departments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system`  tinyint(1) NOT NULL DEFAULT 0,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_item_consumptions_dtl
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_consumptions_dtl`;
CREATE TABLE `shop_item_consumptions_dtl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_qty` decimal(14,5) NOT NULL DEFAULT '0.00',
  `stock_item_uom_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_item_consumptions_hdr
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_consumptions_hdr`;
CREATE TABLE `shop_item_consumptions_hdr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `consumption_date` date NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `sale_item_qty` decimal(14,5) NOT NULL DEFAULT '0.00',
  `sale_item_uom_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_uom_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `combo_sale_item_id` bigint(20) DEFAULT '0',
  `combo_sale_item_uom_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `include_combo_qty` decimal(14,5) DEFAULT '0.00',
  `discount_amount` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'total item wise dicounts',
  `tax_amount` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'total taxes',
  `item_total` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'item price total',
  `round_adjustment` decimal(14,5) NOT NULL DEFAULT '0.00',
  `total` decimal(14,5) DEFAULT NULL COMMENT 'not using this',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'default 0, 0-> open , 1-> closed records',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_shifts
-- ----------------------------
DROP TABLE IF EXISTS `shop_shifts`;
CREATE TABLE `shop_shifts` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shift_type` tinyint(4) NOT NULL,
  `start_time` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `allowed_time_before_start` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `allowed_time_after_start` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interval_start_time` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interval_end_time` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interval_is_payable` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ability to set interval as paid or unpaid. 0-> paid, 1->unpaid default paid',
  `end_time` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `allowed_time_before_end` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `allowed_time_after_end` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(2) NOT NULL,
  `total_hours` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `allow_unscheduled_unpaid_breaks` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ability to allow unscheduled unpaid breaks. 0->Deny unscheduled_unpaid_breaks , 1->allow_unscheduled_unpaid_breaks',
  `overtime_is_payable` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Ability to set overtime is payable. 0-> overtime is unpaid, 1=> overtime is payable',
  `minimum_overtime_limit` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_users
-- ----------------------------
DROP TABLE IF EXISTS `shop_users`;
CREATE TABLE `shop_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_group_id` bigint(20) NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for staff_cost_summary
-- ----------------------------
DROP TABLE IF EXISTS `staff_cost_summary`;
CREATE TABLE `staff_cost_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `week_no` tinyint(2) DEFAULT NULL,
  `summary_date` date DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `shop_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area_codes_id` bigint(20) DEFAULT NULL,
  `area_codes_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `department_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `report_department_id` bigint(20) DEFAULT NULL,
  `report_department_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emp_category_id` bigint(20) DEFAULT NULL,
  `emp_category_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emp_id` bigint(20) DEFAULT NULL,
  `emp_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emp_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cost_per_hour` decimal(14,5) DEFAULT NULL,
  `overtime_pay_rate` decimal(14,5) DEFAULT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `shift_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shift_total_hours` decimal(14,5) DEFAULT NULL,
  `normal_work_hours` decimal(14,5) DEFAULT NULL,
  `normal_cost` decimal(14,5) DEFAULT NULL,
  `overtime_work_hours` decimal(14,5) DEFAULT NULL,
  `overtime_cost` decimal(14,5) DEFAULT NULL,
  `interval_hours` decimal(14,5) DEFAULT NULL,
  `unscheduled_break_hours` decimal(14,5) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1-> closed, 0-> not closed',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for stations
-- ----------------------------
DROP TABLE IF EXISTS `stations`;
CREATE TABLE `stations` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1=counter,2=restaurent. Default 1',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for stock_item_bom
-- ----------------------------
DROP TABLE IF EXISTS `stock_item_bom`;
CREATE TABLE `stock_item_bom` (
  `id`  int(11) NOT NULL ,
  `stock_item_id`  int(11) NOT NULL ,
  `stock_item_qty` decimal(14,5) DEFAULT NULL,
  `bom_item_id`  int(11) NOT NULL ,
  `bom_item_qty`  decimal(15,5) NOT NULL ,
  `base_item_id` int(11) DEFAULT NULL,
  `cost_price`  decimal(15,5) NOT NULL DEFAULT 0.00000 ,
  `remarks`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'publish_status publishe to que table status.default value is 0, (0= to publish 1= published successfully, 2= publication failed)',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_deleted`  tinyint(4) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of stock_item_bom
-- ----------------------------

-- ----------------------------
-- Table structure for stock_item_categories
-- ----------------------------
DROP TABLE IF EXISTS `stock_item_categories`;
CREATE TABLE `stock_item_categories` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `department_id` bigint(14) DEFAULT NULL,
  `super_category_id` bigint(20) DEFAULT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for stock_item_locations
-- ----------------------------
DROP TABLE IF EXISTS `stock_item_locations`;
CREATE TABLE `stock_item_locations` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `storage_id` bigint(14) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for stock_item_storages
-- ----------------------------
DROP TABLE IF EXISTS `stock_item_storages`;
CREATE TABLE `stock_item_storages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_item_id` bigint(20) NOT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `storage_id` bigint(20) NOT NULL,
  `stock_item_locations` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for stock_items
-- ----------------------------
DROP TABLE IF EXISTS `stock_items`;
CREATE TABLE `stock_items` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplier_product_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock_item_attributes` text COLLATE utf8_unicode_ci NULL,
  `stock_item_location` text COLLATE utf8_unicode_ci,
  `stock_item_category_id` bigint(20) DEFAULT NULL,
  `prd_department_id` bigint(20) NULL DEFAULT NULL,
  `part_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `market_valuation_method` tinyint(4)  DEFAULT 0 COMMENT '0 -> Average Cost Price 1-> Last Purchase Price',
  `movement_method` tinyint(4) DEFAULT NULL,
  `uom_id` bigint(20) NOT NULL,
  `stock_uom_id`  bigint(20) NULL DEFAULT NULL,
  `item_weight` decimal(14,5) DEFAULT 1,
  `qty_on_hand` decimal(14,5) DEFAULT NULL,
  `unit_price` decimal(14,5) DEFAULT NULL,
  `optimum_level` decimal(14,5) DEFAULT NULL,
  `reorder_level` decimal(14,5) DEFAULT NULL,
  `reorder_qty` decimal(14,5) DEFAULT NULL,
  `preferred_supplier_id` bigint(20) DEFAULT NULL,
  `last_purchase_unit_price` decimal(14,5) DEFAULT NULL COMMENT 'last purchased price',
  `last_purchase_supplier_id` bigint(20) DEFAULT NULL,
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_calculation_method` tinyint(4) DEFAULT NULL,
  `is_service_item` tinyint(2) NOT NULL DEFAULT '0',
  `is_valid` tinyint(4) DEFAULT 1,
  `is_manufactured` tinyint(4) DEFAULT '0',
  `is_sellable` tinyint(4) NOT NULL DEFAULT '0',
  `is_semi_finished`  tinyint(4) NULL DEFAULT 0,
  `is_finished`  tinyint(4) NULL DEFAULT 0,
  `sales_margin`  int(11) NULL DEFAULT 0,
  `is_sales_margin_percent`  tinyint(4) NULL DEFAULT 0,
  `tax_percentage`  decimal(5,2) NULL DEFAULT 0.00,
  `qty_manufactured` decimal(14,5) DEFAULT NULL,
  `top_consumption_rank` tinyint(2) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for storages
-- ----------------------------
DROP TABLE IF EXISTS `storages`;
CREATE TABLE `storages` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `department_id` bigint(14) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for supplier_invoices
-- ----------------------------
DROP TABLE IF EXISTS `supplier_invoices`;
CREATE TABLE `supplier_invoices` (
  `id` bigint(20) NOT NULL,
  `supp_invoice_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `suplier_id` bigint(20) NOT NULL,
  `po_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `inv_date` datetime NOT NULL,
  `grn_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(14,4) DEFAULT NULL,
  `due_amount` decimal(14,4) DEFAULT NULL,
  `currency_id` bigint(20) NOT NULL,
  `exchange_rate` decimal(14,4) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for suppliers
-- ----------------------------
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE `suppliers` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `company_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `street` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fax` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AP_Code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_local_supplier` tinyint(1) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for `terminal_sync_status`
-- ----------------------------
DROP TABLE IF EXISTS `terminal_sync_status`;
CREATE TABLE `terminal_sync_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_status` tinyint(1) DEFAULT NULL,
  `sync_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for terminal_sync_table_settings
-- ----------------------------
DROP TABLE IF EXISTS `terminal_sync_table_settings`;
CREATE TABLE `terminal_sync_table_settings` (
`id`  int(11) NOT NULL ,
`sync_order`  int(11) NULL DEFAULT NULL ,
`table_id`  int(11) NOT NULL ,
`table_name`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`parent_table_id`  int(11) NOT NULL ,
`table_criteria`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL ,
`order_by`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`column_to_exclude`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`pkey`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`web_param_value`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`is_sync_server_to_terminal`  smallint(1) NULL DEFAULT 0 ,
`is_sync_terminal_to_server`  smallint(1) NULL DEFAULT 0 ,
`is_sync_terminal_to_tab`  smallint(1) NULL DEFAULT 0 ,
`is_sync_tab_to_terminal`  smallint(1) NULL DEFAULT NULL ,
`remarks`  varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL ,
`last_sync_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for system_functions
-- ----------------------------
DROP TABLE IF EXISTS `system_functions`;
CREATE TABLE `system_functions` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `system_group` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `system_sub_group` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_view_applicable` tinyint(1) DEFAULT NULL,
  `is_add_applicable` tinyint(1) DEFAULT NULL,
  `is_edit_applicable` tinyint(1) DEFAULT NULL,
  `is_delete_applicable` tinyint(1) DEFAULT NULL,
  `is_execute_applicable` tinyint(1) DEFAULT NULL,
  `is_publish_applicable` tinyint(4) NOT NULL DEFAULT '0',
  `is_export_applicable` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for system_pages
-- ----------------------------
DROP TABLE IF EXISTS `system_pages`;
CREATE TABLE `system_pages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system_function_id` bigint(20) NOT NULL,
  `page_name` varchar(200) NOT NULL,
  `page_icon` varchar(200) NOT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_params
-- ----------------------------
DROP TABLE IF EXISTS `system_params`;
CREATE TABLE `system_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_format` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `date_separator` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `time_format` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `time_zone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `decimal_places` tinyint(4) NOT NULL DEFAULT 2,
  `default_customer_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `default_taxation_method` tinyint(4) DEFAULT NULL,
  `is_direct_stock_entry` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=PO,1=Direct,2=Both',
  `financial_month`  tinyint(255) NULL DEFAULT 4,
  `week_start`  tinyint(4) NULL DEFAULT 0,
  `smtp_server`  varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `smtp_port`  varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `smtp_mailid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `smtp_password`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `sms_web_service`  varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `sms_userid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `sms_password`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for target_settings_dtl
-- ----------------------------
DROP TABLE IF EXISTS `target_settings_dtl`;
CREATE TABLE `target_settings_dtl` (
  `id` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'mysql insert id not getting correctly when insert in multiple dbs. so change this to varch with format :hdrId-0001(291-D0001)',
  `target_settings_hdr_id` bigint(20) NOT NULL,
  `week_no` tinyint(2) NOT NULL,
  `target_amount` decimal(14,5) NOT NULL,
  `labour_hour` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `labour_cost` decimal(14,5) DEFAULT NULL,
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'Row delete status. 0 is default. 0= not deleted record. 1=deleted record',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for target_settings_hdr
-- ----------------------------
DROP TABLE IF EXISTS `target_settings_hdr`;
CREATE TABLE `target_settings_hdr` (
  `id` bigint(20) NOT NULL,
  `shop_id` bigint(20) NOT NULL,
  `start_year_date` datetime NOT NULL,
  `end_year_date` datetime NOT NULL,
  `week_start_day_name` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `year` int(10) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'Row delete status. 0 is default. 0= not deleted record. 1=deleted record',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for tax_gorup_dtls
-- ----------------------------
DROP TABLE IF EXISTS `tax_gorup_dtls`;
CREATE TABLE `tax_gorup_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tax_group_id` bigint(20) NOT NULL,
  `tax_id` bigint(20) NOT NULL,
  `db_mapping` int(11) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for tax_gorups
-- ----------------------------
DROP TABLE IF EXISTS `tax_gorups`;
CREATE TABLE `tax_gorups` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_bill_level` tinyint(4) DEFAULT NULL,
  `is_before_discount` tinyint(4) DEFAULT NULL,
  `is_before_item_tax` tinyint(4) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for tax_param
-- ----------------------------
DROP TABLE IF EXISTS `tax_param`;
CREATE TABLE `tax_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `default_taxation_method` tinyint(4) NOT NULL DEFAULT '0',
  `default_purchase_taxation_method` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0=>Inclusive of tax,1=>Exclusive of tax,This method is for stock items',
  `claculate_tax_before_discount` tinyint(4) NOT NULL DEFAULT '1',
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_by`  int(11) NULL DEFAULT '0',
  `created_at`  datetime  NULL,
  `updated_by`  int(11) NULL ,
  `updated_at`  datetime NULL,
  `is_system`  tinyint(1) NOT NULL DEFAULT 0,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for taxes
-- ----------------------------
DROP TABLE IF EXISTS `taxes`;
CREATE TABLE `taxes` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `is_item_specific` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=Item Level,1=Bill level',
  `is_tax1_applicable` tinyint(1) NOT NULL DEFAULT 0,
  `tax1_percentage` decimal(5,2) DEFAULT NULL,
  `is_tax2_applicable` tinyint(1) NOT NULL DEFAULT 0,
  `tax2_percentage` decimal(5,2) DEFAULT NULL,
  `is_tax3_applicable` tinyint(1) NOT NULL DEFAULT 0,
  `tax3_percentage` decimal(5,2) DEFAULT NULL,
  `is_sc_applicable` tinyint(1) NOT NULL DEFAULT 0,
  `sc_percentage` decimal(5,2) DEFAULT NULL,
  `is_define_gst` tinyint(1) NOT NULL DEFAULT 0,
  `gst_percentage` decimal(5,2) DEFAULT NULL,
  `is_tax1_included_in_gst` tinyint(1) NOT NULL DEFAULT 0,
  `is_tax2_included_in_gst` tinyint(1) NOT NULL DEFAULT 0,
  `is_tax3_included_in_gst` tinyint(1) NOT NULL DEFAULT 0,
  `is_sc_included_in_gst` tinyint(1) NOT NULL DEFAULT 0,
  `tax1_refund_rate` decimal(14,5) NOT NULL DEFAULT '0.00',
  `tax2_refund_rate` decimal(14,5) NOT NULL DEFAULT '0.00',
  `tax3_refund_rate` decimal(14,5) NOT NULL DEFAULT '0.00',
  `sc_refund_rate` decimal(14,5) NOT NULL DEFAULT '0.00',
  `gst_refund_rate` decimal(14,5) NOT NULL DEFAULT '0.00',
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT ' publishe to que table status.default value is 0,  (0= to publish 1= published successfully, 2= publication failed)',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for temp_sync_queue
-- ----------------------------
DROP TABLE IF EXISTS `temp_sync_queue`;
CREATE TABLE `temp_sync_queue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `record_id` bigint(20) NOT NULL COMMENT 'if rec_id=0 then delete all ',
  `shop_id` bigint(20) NOT NULL,
  `crud_action` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT 'C=insert,U=update,D=delete',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_cashouts
-- ----------------------------
DROP TABLE IF EXISTS `txn_cashouts`;
CREATE TABLE `txn_cashouts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `shift_id`  bigint(20) NOT NULL,
  `cashout_date` date NOT NULL,
  `cashout_time` datetime NOT NULL,
  `title` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(14,5) NOT NULL,
  `remarks` text COLLATE utf8_unicode_ci,
  `created_by` bigint(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `sync_message` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`station_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `txn_cashouts` 
ADD INDEX `idx_txn_cashouts_title` (`title`) USING BTREE,
ADD INDEX `idx_txn_cashouts_station_id` (`station_id`) USING BTREE,
ADD INDEX `idx_txn_cashouts_user_id` (`user_id`) USING BTREE,
ADD INDEX `idx_txn_cashouts_shift_id` (`shift_id`) USING BTREE;

-- ----------------------------
-- Table structure for txn_dr_days
-- ----------------------------
DROP TABLE IF EXISTS `txn_dr_days`;
CREATE TABLE `txn_dr_days` (
  `dr_days_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dr_weeks_id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `total_hours` int(11) NOT NULL,
  PRIMARY KEY (`dr_days_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_dr_details
-- ----------------------------
DROP TABLE IF EXISTS `txn_dr_details`;
CREATE TABLE `txn_dr_details` (
  `dr_details_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dr_weeks_id` bigint(20) NOT NULL,
  `dr_days_id` bigint(20) NOT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `employee_id` bigint(20) NOT NULL,
  `shift_in` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shift_out` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extraRow` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dr_details_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_dr_weeks
-- ----------------------------
DROP TABLE IF EXISTS `txn_dr_weeks`;
CREATE TABLE `txn_dr_weeks` (
  `dr_weeks_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shift_groups_id` bigint(20) NOT NULL,
  `start_date` datetime NOT NULL,
  `total_hours` int(11) NOT NULL,
  PRIMARY KEY (`dr_weeks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_po_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_po_dtls`;
CREATE TABLE `txn_po_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `po_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `po_line_no` tinyint(4) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `stock_item_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `qty` decimal(14,5) NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `rate` decimal(14,5) DEFAULT NULL,
  `dtl_tax_id` int(11) DEFAULT NULL,
  `tax_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_tax_amount` decimal(14,5) DEFAULT NULL,
  `item_amount` decimal(14,5) DEFAULT NULL,
  `tax_calculation_method` tinyint(2) DEFAULT NULL,
  `dtl_tax1_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax2_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax3_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax4_amount` decimal(14,5) DEFAULT NULL,
  `discount_amount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) NOT NULL,
  `expected_delivery_date` date NOT NULL,
  `delivered_qty` decimal(14,5) DEFAULT '0.00',
  `rejected_qty` decimal(14,5) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_po_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_po_hdrs`;
CREATE TABLE `txn_po_hdrs` (
  `po_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `hdr_tax_calculation_method` tinyint(2) DEFAULT NULL,
  `performa_quote_ref` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rate_contract_ref` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_tax_in_dtl` tinyint(4) DEFAULT '5',
  `po_type` tinyint(4) NOT NULL,
  `po_status` tinyint(4) NOT NULL COMMENT '0=Approval Waiting,1=Approved,2=Rejected,3=Closed,4=All,5=Reopen',
  `po_date` date NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  `po_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `ship_to_address_id` bigint(20) NOT NULL,
  `bill_to_address_id` bigint(20) NOT NULL,
  `shipment_id` bigint(20) DEFAULT NULL,
  `terms` text COLLATE utf8_unicode_ci,
  `validity_days` tinyint(4) DEFAULT NULL,
  `currency_id` bigint(20) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `payment_term` text COLLATE utf8_unicode_ci,
  `detail_total` decimal(14,5) NOT NULL,
  `tax_id` int(11) DEFAULT NULL,
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `tax4_amount` decimal(14,5) DEFAULT NULL,
  `detail_total_tax_amount` decimal(14,5) NOT NULL,
  `shipping_amount` decimal(14,5) DEFAULT NULL,
  `other_amount` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) NOT NULL,
  `stockin_status` tinyint(4) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`po_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_po_template_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_po_template_dtls`;
CREATE TABLE `txn_po_template_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `po_template__hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `stock_item_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `rate` decimal(14,5) DEFAULT NULL,
  `dtl_tax_id` bigint(20) DEFAULT NULL,
  `tax_calculation_method` tinyint(2) DEFAULT NULL,
  `dtl_tax1_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax2_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax3_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax4_amount` decimal(14,5) DEFAULT NULL,
  `total_tax_amount` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_po_template_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_po_template_hdrs`;
CREATE TABLE `txn_po_template_hdrs` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  `ship_to_address_id` bigint(20) NOT NULL,
  `bill_to_address_id` bigint(20) NOT NULL,
  `shipment_id` bigint(20) DEFAULT NULL,
  `terms` text COLLATE utf8_unicode_ci,
  `currency_id` bigint(20) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `payment_term` text COLLATE utf8_unicode_ci,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_purchase_return_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_purchase_return_dtls`;
CREATE TABLE `txn_purchase_return_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_return_hdr_id` bigint(20) NOT NULL,
  `po_dtls_id` bigint(14) DEFAULT NULL,
  `po_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stk_dtls_id` bigint(14) DEFAULT NULL,
  `grn_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `department_id` bigint(14) DEFAULT NULL,
  `storage_id` bigint(14) DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `rate` decimal(14,5) NOT NULL,
  `return_reason` text COLLATE utf8_unicode_ci NOT NULL,
  `stock_item_locations` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty_converted` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_purchase_return_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_purchase_return_hdrs`;
CREATE TABLE `txn_purchase_return_hdrs` (
  `id` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  `purchase_return_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ship_to_address_id` bigint(20) NOT NULL,
  `shipment_id` bigint(20) NOT NULL,
  `terms` text COLLATE utf8_unicode_ci NOT NULL,
  `detail_total` decimal(14,5) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_rfp_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_rfp_dtls`;
CREATE TABLE `txn_rfp_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rfp_hdr_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `rfp_line_no` tinyint(4) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock_item_code` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `qty` decimal(14,5) DEFAULT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `expected_delivery_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_rfp_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_rfp_hdrs`;
CREATE TABLE `txn_rfp_hdrs` (
  `id` bigint(20) NOT NULL,
  `rfp_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `rfp_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0=Approval Waiting,1=Approved,2=Processed,3=Rejected,4=Closed,5=All',
  `rfp_date` date NOT NULL,
  `rfp_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `approved_by` bigint(14) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` bigint(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_delivery_note_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_delivery_note_dtls`;
CREATE TABLE `txn_sales_delivery_note_dtls` (
  `id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `sales_delivery_note_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sales_item_id` bigint(20) NOT NULL,
  `sales_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_id` bigint(20) NOT NULL,
  `sub_class_code` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty`decimal(14,5) NOT NULL,
  `tax_calculation_method` tinyint(4) DEFAULT '0',
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_tax1_applied` tinyint(1) DEFAULT '0',
  `is_open` tinyint(4) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `uom_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `package_id` bigint(20) NOT NULL,
  `uom_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `is_combo_item` tinyint(1) DEFAULT '0',
  `fixed_price` decimal(14,5) NOT NULL,
  `tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax1_pc` decimal(14,5) DEFAULT NULL,
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `is_tax2_applied` tinyint(1) DEFAULT '0',
  `tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax2_pc` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `is_tax3_applied` tinyint(1) DEFAULT '0',
  `tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax3_pc` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `is_gst_applied` tinyint(1) DEFAULT NULL,
  `gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_pc` decimal(14,5) DEFAULT NULL,
  `gst_amount` decimal(14,5) DEFAULT NULL,
  `is_tax1_included_in_gst` tinyint(1) DEFAULT '0',
  `is_tax2_included_in_gst` tinyint(1) DEFAULT '0',
  `is_tax3_included_in_gst` tinyint(1) DEFAULT '0',
  `is_sc_included_in_gst` tinyint(1) DEFAULT '0',
  `is_sc_applied` tinyint(1) DEFAULT NULL,
  `sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sc_pc` decimal(14,5) DEFAULT NULL,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `item_total` decimal(14,5) NOT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(1) DEFAULT NULL,
  `discount_is_overridable` tinyint(1) DEFAULT NULL,
  `discount_is_item_specific` tinyint(1) DEFAULT NULL,
  `discount_permitted_for` tinyint(1) DEFAULT NULL,
  `discount_amount` decimal(14,5) NOT NULL,
  `round_adjustment` decimal(2,2) DEFAULT NULL,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `is_printed_to_kitchen` tinyint(4) DEFAULT '0',
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_void` tinyint(4) DEFAULT '0',
  `tax_amount` decimal(14,5) DEFAULT NULL,
  `expected_delivery_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_delivery_note_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_delivery_note_hdrs`;
CREATE TABLE `txn_sales_delivery_note_hdrs` (
  `sales_delivery_note_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_no` bigint(20) NOT NULL,
  `shop_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `station_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_time` time DEFAULT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `detail_total` decimal(14,5) DEFAULT NULL,
  `total_tax1` decimal(14,5) DEFAULT NULL,
  `total_tax2` decimal(14,5) DEFAULT NULL,
  `total_tax3` decimal(14,5) DEFAULT NULL,
  `total_sc` decimal(14,5) DEFAULT NULL,
  `total_gst` decimal(14,5) DEFAULT NULL,
  `total_detail_discount` decimal(14,5) DEFAULT NULL,
  `final_adjustment_amount` decimal(14,5) DEFAULT NULL,
  `total_amount` decimal(14,5) DEFAULT NULL,
  `total_amount_paid` decimal(14,5) DEFAULT NULL,
  `total_balance` decimal(14,5) DEFAULT NULL,
  `actual_balance_paid` decimal(14,5) DEFAULT NULL,
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `delivery_note_status` tinyint(4) DEFAULT NULL COMMENT '0=Approval Waiting,1=Approved,2=Rejected,3=Closed,4=All',
  `total_print_count` tinyint(4) DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL,
  `sync_message` longtext DEFAULT NULL,
  `currency_id` bigint(20) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `ship_to_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_to_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `delivery_note_reference` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `terms` text COLLATE utf8_unicode_ci,
  `payment_term` text COLLATE utf8_unicode_ci,
  `shipping_amount` decimal(14,5) DEFAULT NULL,
  `other_amount` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`sales_delivery_note_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_delivery_note_payments
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_delivery_note_payments`;
CREATE TABLE `txn_sales_delivery_note_payments` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `delivery_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_mode` tinyint(2) DEFAULT NULL,
  `paid_amount` decimal(14,5) DEFAULT NULL,
  `card_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_no` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_on_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_expiry_month` bigint(20) DEFAULT NULL,
  `card_expiry_year` decimal(4,0) DEFAULT NULL,
  `card_approval_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  `voucher_value` decimal(14,5) DEFAULT NULL,
  `voucher_count` tinyint(2) DEFAULT NULL,
  `cachier_id` bigint(20) DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_invoice_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_invoice_dtls`;
CREATE TABLE `txn_sales_invoice_dtls` (
  `id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `sales_invoice_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sales_item_id` bigint(20) NOT NULL,
  `sales_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_id` bigint(20) DEFAULT NULL,
  `sub_class_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sub_class_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  `tax_calculation_method` tinyint(4) DEFAULT '0',
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_tax1_applied` tinyint(1) DEFAULT '0',
  `is_open` tinyint(4) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `uom_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `uom_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `is_combo_item` tinyint(1) DEFAULT '0',
  `fixed_price` decimal(14,5) NOT NULL,
  `tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax1_pc` decimal(14,5) DEFAULT NULL,
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `is_tax2_applied` tinyint(1) DEFAULT '0',
  `tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax2_pc` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `is_tax3_applied` tinyint(1) DEFAULT '0',
  `tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax3_pc` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `is_gst_applied` tinyint(1) DEFAULT NULL,
  `gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_pc` decimal(14,5) DEFAULT NULL,
  `gst_amount` decimal(14,5) DEFAULT NULL,
  `is_tax1_included_in_gst` tinyint(1) DEFAULT '0',
  `is_tax2_included_in_gst` tinyint(1) DEFAULT '0',
  `is_tax3_included_in_gst` tinyint(1) DEFAULT '0',
  `is_sc_included_in_gst` tinyint(1) DEFAULT '0',
  `is_sc_applied` tinyint(1) DEFAULT NULL,
  `sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sc_pc` decimal(14,5) DEFAULT NULL,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `item_total` decimal(14,5) NOT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(1) DEFAULT NULL,
  `discount_is_overridable` tinyint(1) DEFAULT NULL,
  `discount_is_item_specific` tinyint(1) DEFAULT NULL,
  `discount_permitted_for` tinyint(1) DEFAULT NULL,
  `discount_amount` decimal(14,5) NOT NULL,
  `round_adjustment` decimal(14,5) DEFAULT NULL,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `is_printed_to_kitchen` tinyint(4) DEFAULT '0',
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_void` tinyint(4) DEFAULT '0',
  `tax_amount` decimal(14,5) DEFAULT NULL,
  `expected_invoice_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_invoice_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_invoice_hdrs`;
CREATE TABLE `txn_sales_invoice_hdrs` (
  `sales_invoice_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_no` bigint(20) NOT NULL,
  `approve_sales_order` tinyint(2) DEFAULT NULL,
  `shop_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `station_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_time` time DEFAULT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `customer_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `detail_total` decimal(14,5) DEFAULT NULL,
  `total_tax1` decimal(14,5) DEFAULT NULL,
  `total_tax2` decimal(14,5) DEFAULT NULL,
  `total_tax3` decimal(14,5) DEFAULT NULL,
  `total_sc` decimal(14,5) DEFAULT NULL,
  `total_gst` decimal(14,5) DEFAULT NULL,
  `total_detail_discount` decimal(14,5) DEFAULT NULL,
  `final_adjustment_amount` decimal(14,5) DEFAULT NULL,
  `total_amount` decimal(14,5) DEFAULT NULL,
  `total_amount_paid` decimal(14,5) DEFAULT NULL,
  `total_balance` decimal(14,5) DEFAULT NULL,
  `actual_balance_paid` decimal(14,5) DEFAULT NULL,
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sales_invoice_status` tinyint(4) DEFAULT NULL COMMENT '0=Approval Waiting,1=Approved,2=Rejected,3=Closed,4=All',
  `total_print_count` tinyint(4) DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL,
  `sync_message` longtext DEFAULT NULL,
  `currency_id` bigint(20) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `ship_to_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_to_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoice_reference` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `terms` text COLLATE utf8_unicode_ci,
  `payment_term` text COLLATE utf8_unicode_ci,
  `shipping_amount` decimal(14,5) DEFAULT NULL,
  `other_amount` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `detail_total_tax_amount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) DEFAULT NULL,
  `hdr_tax_calculation_method` tinyint(2) DEFAULT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`sales_invoice_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_invoice_payments
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_invoice_payments`;
CREATE TABLE `txn_sales_invoice_payments` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `invoice_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_mode` tinyint(2) DEFAULT NULL,
  `paid_amount` decimal(14,5) DEFAULT NULL,
  `card_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_no` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_on_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_expiry_month` bigint(20) DEFAULT NULL,
  `card_expiry_year` decimal(4,0) DEFAULT NULL,
  `card_approval_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  `voucher_value` decimal(14,5) DEFAULT NULL,
  `voucher_count` tinyint(2) DEFAULT NULL,
  `cachier_id` bigint(20) DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_order_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_order_dtls`;
CREATE TABLE `txn_sales_order_dtls` (
  `id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sales_item_id` bigint(20) NOT NULL,
  `sales_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_id` bigint(20) DEFAULT NULL,
  `sub_class_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sub_class_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty`decimal(14,5) NOT NULL,
  `tax_calculation_method` tinyint(4) DEFAULT '0',
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_tax1_applied` tinyint(1) DEFAULT '0',
  `is_open` tinyint(4) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `uom_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `uom_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `is_combo_item` tinyint(1) DEFAULT '0',
  `fixed_price` decimal(14,5) NOT NULL,
  `tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax1_pc` decimal(14,5) DEFAULT NULL,
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `is_tax2_applied` tinyint(1) DEFAULT '0',
  `tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax2_pc` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `is_tax3_applied` tinyint(1) DEFAULT '0',
  `tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax3_pc` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `is_gst_applied` tinyint(1) DEFAULT NULL,
  `gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_pc` decimal(14,5) DEFAULT NULL,
  `gst_amount` decimal(14,5) DEFAULT NULL,
  `is_tax1_included_in_gst` tinyint(1) DEFAULT '0',
  `is_tax2_included_in_gst` tinyint(1) DEFAULT '0',
  `is_tax3_included_in_gst` tinyint(1) DEFAULT '0',
  `is_sc_included_in_gst` tinyint(1) DEFAULT '0',
  `is_sc_applied` tinyint(1) DEFAULT NULL,
  `sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sc_pc` decimal(14,5) DEFAULT NULL,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `item_total` decimal(14,5) NOT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(1) DEFAULT NULL,
  `discount_is_overridable` tinyint(1) DEFAULT NULL,
  `discount_is_item_specific` tinyint(1) DEFAULT NULL,
  `discount_permitted_for` tinyint(1) DEFAULT NULL,
  `discount_amount` decimal(14,5) NOT NULL,
  `round_adjustment` decimal(2,2) DEFAULT NULL,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `is_printed_to_kitchen` tinyint(4) DEFAULT '0',
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_void` tinyint(4) DEFAULT '0',
  `tax_amount` decimal(14,5) DEFAULT NULL,
  `expected_sales_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_order_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_order_hdrs`;
CREATE TABLE `txn_sales_order_hdrs` (
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_no` bigint(20) NOT NULL,
  `shop_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `station_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_time` time DEFAULT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `customer_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `detail_total` decimal(14,5) DEFAULT NULL,
  `total_tax1` decimal(14,5) DEFAULT NULL,
  `total_tax2` decimal(14,5) DEFAULT NULL,
  `total_tax3` decimal(14,5) DEFAULT NULL,
  `total_sc` decimal(14,5) DEFAULT NULL,
  `total_gst` decimal(14,5) DEFAULT NULL,
  `total_detail_discount` decimal(14,5) DEFAULT NULL,
  `final_adjustment_amount` decimal(14,5) DEFAULT NULL,
  `total_amount` decimal(14,5) DEFAULT NULL,
  `total_amount_paid` decimal(14,5) DEFAULT NULL,
  `total_balance` decimal(14,5) DEFAULT NULL,
  `actual_balance_paid` decimal(14,5) DEFAULT NULL,
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sales_order_status` tinyint(4) DEFAULT NULL COMMENT '0=Approval Waiting,1=Approved,2=Rejected,3=Closed,4=All',
  `total_print_count` tinyint(4) DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL,
  `sync_message` longtext DEFAULT NULL,
  `currency_id` bigint(20) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `ship_to_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill_to_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sales_order_reference` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `terms` text COLLATE utf8_unicode_ci,
  `payment_term` text COLLATE utf8_unicode_ci,
  `shipping_amount` decimal(14,5) DEFAULT NULL,
  `other_amount` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `detail_total_tax_amount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) DEFAULT NULL,
  `hdr_tax_calculation_method` tinyint(2) DEFAULT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_sales_order_payments
-- ----------------------------
DROP TABLE IF EXISTS `txn_sales_order_payments`;
CREATE TABLE `txn_sales_order_payments` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_mode` tinyint(2) DEFAULT NULL,
  `paid_amount` decimal(14,5) DEFAULT NULL,
  `card_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_no` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_on_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_expiry_month` bigint(20) DEFAULT NULL,
  `card_expiry_year` decimal(4,0) DEFAULT NULL,
  `card_approval_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  `voucher_value` decimal(14,5) DEFAULT NULL,
  `voucher_count` tinyint(2) DEFAULT NULL,
  `cachier_id` bigint(20) DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_shop_item_consumptions_dtl
-- ----------------------------
DROP TABLE IF EXISTS `txn_shop_item_consumptions_dtl`;
CREATE TABLE `txn_shop_item_consumptions_dtl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) DEFAULT NULL,
  `stock_item_qty` decimal(14,5) DEFAULT '0.00',
  `stock_item_uom_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_shop_item_consumptions_hdr
-- ----------------------------
DROP TABLE IF EXISTS `txn_shop_item_consumptions_hdr`;
CREATE TABLE `txn_shop_item_consumptions_hdr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `consumption_date` date NOT NULL,
  `sale_item_id` bigint(20) DEFAULT NULL,
  `sale_item_qty` decimal(14,5) NOT NULL,
  `sale_item_uom_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stock_item_id` bigint(20) DEFAULT NULL,
  `stock_item_uom_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_sale_item_id` bigint(20) DEFAULT '0',
  `combo_sale_item_uom_code` varchar(20)  COLLATE utf8_unicode_ci DEFAULT NULL,
  `include_combo_qty`decimal(14,5) DEFAULT '0.00',
  `discount_amount` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'total item wise dicounts',
  `tax_amount` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'total taxes',
  `item_total` decimal(14,5) NOT NULL DEFAULT '0.00' COMMENT 'item price total',
  `round_adjustment` decimal(14,5) NOT NULL DEFAULT '0.00',
  `total` decimal(14,5) DEFAULT NULL COMMENT 'not using this',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'default 0, 0-> open , 1-> closed records',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_staff_attendance
-- ----------------------------
DROP TABLE IF EXISTS `txn_staff_attendance`;
CREATE TABLE `txn_staff_attendance` (
  `id` bigint(14) NOT NULL AUTO_INCREMENT,
  `pos_id` bigint(14) NOT NULL DEFAULT '0',
  `employee_id` bigint(14) NOT NULL,
  `shift_no` bigint(14) NOT NULL,
  `shift_id` bigint(14) DEFAULT NULL,
  `shift_start_date` date NOT NULL,
  `shift_start_time` time NOT NULL,
  `shift_end_date` date DEFAULT NULL,
  `shift_end_time` time DEFAULT NULL,
  `pos_date_start` date DEFAULT NULL COMMENT 'pos _date when start shiift starts',
  `pos_date_end` date DEFAULT NULL COMMENT 'pos _date when start shiift ends',
  `is_processed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=not processed 1=processed',
  `unscheduled_breakout` time DEFAULT NULL,
  `unscheduled_breakin` time DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0= Error, 1=OK',
  `sync_message` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_staff_leave
-- ----------------------------
DROP TABLE IF EXISTS `txn_staff_leave`;
CREATE TABLE `txn_staff_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) NOT NULL,
  `application_date` date NOT NULL,
  `leave_type` tinyint(1) NOT NULL,
  `duration` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `day_from` date DEFAULT NULL,
  `is_full_day_from` tinyint(1) DEFAULT '0',
  `day_to` date DEFAULT NULL,
  `is_full_day_to` tinyint(1) DEFAULT '0',
  `time_from` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time_to` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stk_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_stk_dtls`;
CREATE TABLE `txn_stk_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_in_hdr_id` bigint(20) NOT NULL,
  `grn_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `po_dtls_id` bigint(20) DEFAULT NULL,
  `po_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `stock_item_id` bigint(14) NOT NULL,
  `stock_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `stock_item_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `department_id` bigint(14) DEFAULT NULL,
  `storage_id` bigint(14) DEFAULT NULL,
  `stock_item_locations` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  `package_id` bigint(14) DEFAULT NULL,
  `uom_id` bigint(14) NOT NULL,
  `is_replacement` tinyint(4) NOT NULL,
  `qty_converted` decimal(14,5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stk_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_stk_hdrs`;
CREATE TABLE `txn_stk_hdrs` (
  `id` bigint(20) NOT NULL,
  `grn_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` date NOT NULL,
  `time` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `received_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `verified_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `approved_by` bigint(14) DEFAULT NULL,
  `shipment_id` bigint(14) DEFAULT NULL,
  `is_direct_stock_entry` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=Non direct,1=Direct',
  `is_cash_settled` tinyint(1) DEFAULT NULL COMMENT '0=Not settled,1=Settled',
  `created_by` bigint(12) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(12) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stk_out_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_stk_out_dtls`;
CREATE TABLE `txn_stk_out_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_out_hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `department_id` bigint(14) NOT NULL,
  `storage_id` bigint(14) NOT NULL,
  `to_department_id` bigint(14) NOT NULL,
  `to_storage_id` bigint(14) NOT NULL,
  `stock_item_locations` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `qty` decimal(14,5) NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `qty_converted` decimal(14,5) NOT NULL,
  `last_purchase_unit_price` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stk_out_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_stk_out_hdrs`;
CREATE TABLE `txn_stk_out_hdrs` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL,
  `time` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `issued_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `received_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `approved_by` bigint(14) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stock_adjustment_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_stock_adjustment_dtls`;
CREATE TABLE `txn_stock_adjustment_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_adjustment_hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(11) NOT NULL,
  `department_id` bigint(14) DEFAULT NULL,
  `storage_id` bigint(20) DEFAULT NULL,
  `stock_item_locations` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `system_qty` decimal(14,5) NOT NULL,
  `actual_qty` decimal(14,5) NOT NULL,
  `diff_qty` decimal(14,5) NOT NULL,
  `uom_id` bigint(11) NOT NULL,
  `converted_qty` decimal(14,5) DEFAULT NULL,
  `reason` text COLLATE utf8_unicode_ci,
  `last_purchase_unit_price` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stock_adjustment_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_stock_adjustment_hdrs`;
CREATE TABLE `txn_stock_adjustment_hdrs` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` date NOT NULL,
  `time` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `adjusted_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `approved_by` bigint(11) DEFAULT NULL,
  `created_by` bigint(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `generated_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'txn_stock_adjustment_history.generated_id',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stock_adjustment_history
-- ----------------------------
DROP TABLE IF EXISTS `txn_stock_adjustment_history`;
CREATE TABLE `txn_stock_adjustment_history` (
  `generated_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `generated_date` datetime NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1 means deleted',
  PRIMARY KEY (`generated_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_stock_disposals
-- ----------------------------
DROP TABLE IF EXISTS `txn_stock_disposals`;
CREATE TABLE `txn_stock_disposals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_item_id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `storage_id` bigint(20) NOT NULL,
  `qty` decimal(14,5) NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) NOT NULL,
  `dispose_reason` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dispose_date` date NOT NULL,
  `dispose_by` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `approved_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty_converted` decimal(14,5) NOT NULL,
  `created_by` bigint(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `last_purchase_unit_price` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_supplier_invoice_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_supplier_invoice_dtls`;
CREATE TABLE `txn_supplier_invoice_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_invoice_hdr_id` bigint(20) NOT NULL,
  `supp_invoice_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `po_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `ship_to_address_id` bigint(20) DEFAULT NULL,
  `bill_to_address_id` bigint(20) DEFAULT NULL,
  `terms` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `currency_id` bigint(20) DEFAULT NULL,
  `exchange_rate` decimal(14,5) DEFAULT NULL,
  `dtail_total` decimal(14,5) NOT NULL,
  `dtl_tax_id` int(11) DEFAULT NULL,
  `dtl_tax1_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax2_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax3_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax4_amount` decimal(14,5) DEFAULT NULL,
  `dtl_total_tax_amount` decimal(14,5) DEFAULT NULL,
  `dtl_tax_calculation_method` tinyint(2) DEFAULT NULL,
  `shipping_amount` decimal(14,5) DEFAULT NULL,
  `other_amount` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_supplier_invoice_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `txn_supplier_invoice_hdrs`;
CREATE TABLE `txn_supplier_invoice_hdrs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supp_invoice_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `inv_date` date DEFAULT NULL,
  `grn_no` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `amount` decimal(14,5) NOT NULL,
  `due_amount` decimal(14,5) NOT NULL,
  `total_tax_amount` decimal(14,5) DEFAULT NULL,
  `approved_by` bigint(20) NOT NULL,
  `shipment_id` bigint(20) DEFAULT NULL,
  `currency_id` bigint(20) NOT NULL,
  `exchange_rate` decimal(14,5) NOT NULL,
  `is_direct_stock_entry` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=Non direct,1=Direct',
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_supplier_invoice_sub_dtls
-- ----------------------------
DROP TABLE IF EXISTS `txn_supplier_invoice_sub_dtls`;
CREATE TABLE `txn_supplier_invoice_sub_dtls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_invoice_hdr_id` bigint(20) NOT NULL,
  `supplier_invoice_dtl_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `stock_item_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  `package_id` bigint(20) DEFAULT NULL,
  `qty` decimal(14,5) NOT NULL,
  `po_qty` decimal(14,5) DEFAULT NULL,
  `rate` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `total` decimal(14,5) NOT NULL,
  `sub_dtl_tax_id` int(11) DEFAULT NULL,
  `tax_calculation_method` tinyint(2) DEFAULT NULL,
  `sub_dtl_tax1_amount` decimal(14,5) DEFAULT NULL,
  `sub_dtl_tax2_amount` decimal(14,5) DEFAULT NULL,
  `sub_dtl_tax3_amount` decimal(14,5) DEFAULT NULL,
  `sub_dtl_tax4_amount` decimal(14,5) DEFAULT NULL,
  `sub_dtl_total_tax_amount` decimal(14,5) DEFAULT NULL,
  `item_amount` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_supplier_invoices
-- ----------------------------
DROP TABLE IF EXISTS `txn_supplier_invoices`;
CREATE TABLE `txn_supplier_invoices` (
  `id` bigint(20) NOT NULL,
  `supp_invoice_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  `po_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `inv_date` date DEFAULT NULL,
  `grn_no` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `amount` decimal(14,5) NOT NULL,
  `due_amount` decimal(14,5) NOT NULL,
  `currency_id` bigint(20) NOT NULL,
  `exchange_rate` decimal(14,5) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_system_counter
-- ----------------------------
DROP TABLE IF EXISTS `txn_system_counter`;
CREATE TABLE `txn_system_counter` (
  `counter_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `last_generated_counter` bigint(20) NOT NULL,
  `format` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `padwithzero` tinyint(4) NOT NULL,
  `counter_rule` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'DAILY_RESET,MONTHLY_RESET,YEARLY_RESET,Null',
  `last_generated_counter_withformat` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `last_generated_at` date DEFAULT NULL,
  PRIMARY KEY (`counter_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for uoms
-- ----------------------------
DROP TABLE IF EXISTS `uoms`;
CREATE TABLE `uoms` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(251) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_compound` tinyint(1) DEFAULT 0,
  `base_uom_id` bigint(20) DEFAULT NULL,
  `compound_unit`  decimal(14,5) NULL DEFAULT NULL,
  `uom_symbol` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL,
  `decimal_places` tinyint(4) DEFAULT 2,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for tally_uom_mapping
-- ----------------------------
DROP TABLE IF EXISTS `tally_uom_mapping`;
CREATE TABLE `tally_uom_mapping` (
`id`  bigint(20) NOT NULL ,
`code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for tally_vouchers
-- ----------------------------
DROP TABLE IF EXISTS `tally_vouchers`;
CREATE TABLE `tally_vouchers` (
  `sales_voucher` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `refund_voucher` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `addl_ledger` varchar(250) COLLATE utf8_unicode_ci NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for user_group_functions
-- ----------------------------
DROP TABLE IF EXISTS `user_group_functions`;
CREATE TABLE `user_group_functions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_group_id` bigint(20) NOT NULL,
  `system_function_id` bigint(20) NOT NULL,
  `can_view` tinyint(1) DEFAULT NULL,
  `can_add` tinyint(1) DEFAULT NULL,
  `can_edit` tinyint(1) DEFAULT NULL,
  `can_delete` tinyint(1) DEFAULT NULL,
  `can_execute` tinyint(1) DEFAULT NULL,
  `can_publish` tinyint(1) DEFAULT NULL,
  `can_export` tinyint(1) DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for user_groups
-- ----------------------------
DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `user_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 means is hq user, 1-> shop user',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `card_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_group_id` int(11) NOT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `password` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `valid_from` date DEFAULT NULL,
  `valid_to` date DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastlogin_date`datetime NULL,
  `token_id` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token_expire_time` datetime DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for voucher_class
-- ----------------------------
DROP TABLE IF EXISTS `voucher_class`;
CREATE TABLE `voucher_class` (
  `id` int(11) NOT NULL,
  `code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description`  varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for voucher_types
-- ----------------------------
DROP TABLE IF EXISTS `voucher_types`;
CREATE TABLE `voucher_types` (
  `id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `voucher_type` bigint(20) DEFAULT NULL,
  `value` decimal(14,5) NOT NULL DEFAULT '0.00',
  `is_overridable` tinyint(1) NOT NULL DEFAULT '0',
  `is_change_payable` tinyint(1) NOT NULL DEFAULT '0',
  `account_code` varchar(250) COLLATE utf8_unicode_ci NULL,
  `is_valid` tinyint(1) NOT NULL DEFAULT '1',
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `is_synchable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether data will synch with HQ data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for weekly_dashboard_sales
-- ----------------------------
DROP TABLE IF EXISTS `weekly_dashboard_sales`;
CREATE TABLE `weekly_dashboard_sales` (
  `sales_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `week_no` int(2) DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `sales_amount` decimal(14,5) NOT NULL,
  `sales_target` decimal(14,5) NOT NULL,
  `item_sold` decimal(14,5) NOT NULL DEFAULT '0.00',
  `transactions` int(11) NOT NULL,
  `staff_hours` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `staff_hours_target` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `labour_cost` int(11) NOT NULL,
  `labour_cost_target` decimal(14,5) NOT NULL,
  `total_tax` decimal(14,5) NOT NULL,
  `sales_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1-> calculated and closed past week, 0-> current week ',
  PRIMARY KEY (`sales_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for kitchen_printers
-- ----------------------------
DROP TABLE IF EXISTS `kitchen_printers`;
CREATE TABLE `kitchen_printers`(
  `id`  bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `kitchen_id` bigint(20) NOT NULL,
  `no_copies` int(2) DEFAULT 1,
  `is_paper_cut_on` tinyint(1) NOT NULL DEFAULT '0',
  `paper_cut_code` varchar(250) DEFAULT NULL,
  `is_master` tinyint(1) NOT NULL DEFAULT '0',
  `use_alt_language`  int(2) DEFAULT 0,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for stock_in_hdr
-- ----------------------------
DROP TABLE IF EXISTS `stock_in_hdr`;
CREATE TABLE `stock_in_hdr` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `type` bigint(20) DEFAULT NULL COMMENT '1 -> Stock In , 2 -> Adjuxtment, 3-> Disposal,4-> Sales' ,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `stock_in_hdr`
ADD INDEX `idx_stock_in_hdr_type` (`type`) USING BTREE ,
ADD INDEX `idx_stock_in_hdr_date` (`date`) USING BTREE ;

-- ----------------------------
-- Table structure for stock_in_dtl
-- ----------------------------
DROP TABLE IF EXISTS `stock_in_dtl`;
CREATE TABLE `stock_in_dtl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_in_hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) DEFAULT NULL,
  `ext_ref_no`  varchar(50) NULL,
  `description` varchar(100) DEFAULT NULL,
  `qty_received` decimal(14,5) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `stock_in_dtl`
ADD INDEX `idx_stock_in_dtl_stock_in_hdr_id` (`stock_in_hdr_id`) USING BTREE ,
ADD INDEX `idx_stock_in_dtl_stock_item_id` (`stock_item_id`) USING BTREE ;




 
-- ----------------------------
-- Table structure for cashier_shifts
-- ----------------------------
DROP TABLE IF EXISTS `cashier_shifts_hist`;
CREATE TABLE `cashier_shifts_hist` (
  `auto_id` bigint(20) NOT NULL ,
  `cashier_id` bigint(20) NOT NULL,
  `pos_id` bigint(14) NOT NULL DEFAULT '0',
  `opening_date` date DEFAULT NULL COMMENT 'POS open date',
  `opening_time` datetime DEFAULT NULL COMMENT 'Current system date and time ',
  `shift_id` bigint(20) NOT NULL,
  `opening_till_id` bigint(20) DEFAULT NULL COMMENT 'Till  ID to which this record is attached',
  `opening_float` decimal(14,5) DEFAULT NULL,
  `closing_date` date DEFAULT NULL COMMENT 'POS closing date',
  `closing_time` datetime DEFAULT NULL COMMENT 'Current system date and time',
  `collection_cash` decimal(14,5) DEFAULT NULL,
  `collection_card` decimal(14,5) DEFAULT NULL,
  `collection_voucher` decimal(14,5) DEFAULT NULL,
  `collection_company` decimal(14,5) DEFAULT NULL,
  `collection_online` decimal(14,5) DEFAULT NULL,
  `daily_cashout` decimal(14,5) DEFAULT NULL,
  `closing_float` decimal(14,5) DEFAULT NULL,
  `balance_cash` decimal(14,5) DEFAULT '0.00000',
  `balance_voucher` decimal(14,5) DEFAULT '0.00000',
  `balance_voucher_returned` decimal(14,5) DEFAULT '0.00000',
  `cash_out` decimal(14,5) DEFAULT '0.00000',
  `cash_refund` decimal(14,5) DEFAULT '0.00000',
  `card_refund` decimal(14,5) DEFAULT '0.00000',
  `voucher_refund` decimal(14,5) DEFAULT '0.00000',
  `company_refund` decimal(14,5) DEFAULT '0.00000',
  `online_refund` decimal(14,5) DEFAULT '0.00000',
  `total_refund` decimal(14,5) DEFAULT '0.00000',
  `sync_status` tinyint(4) DEFAULT '0',
  `sync_message` longtext COLLATE utf8_unicode_ci,
  `is_open_till` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`auto_id`,`pos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_combo_contents
-- ----------------------------
DROP TABLE IF EXISTS `order_combo_contents_hist`;
CREATE TABLE `order_combo_contents_hist` (
  `id` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `combo_content_row_id` int(11) NOT NULL,
  `content_item_id` bigint(20) NOT NULL,
  `qty` decimal(14,5) NOT NULL,
  `price_diff` decimal(14,5) NOT NULL,
  `combo_sale_item_id` bigint(20) NOT NULL,
  `is_substitutable` tinyint(4) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_customers
-- ----------------------------
DROP TABLE IF EXISTS `order_customers_hist`;
CREATE TABLE `order_customers_hist` (
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tin` varchar(2255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_reg_type` bigint(20) DEFAULT '2',
  `gst_party_type` bigint(20) DEFAULT '1',
  `is_ar` tinyint(1) NOT NULL DEFAULT '0',
  `customer_type` int(11) DEFAULT '0',
  `remarks` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_discounts
-- ----------------------------
DROP TABLE IF EXISTS `order_discounts_hist`;
CREATE TABLE `order_discounts_hist` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_payment_hdr_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `discount_id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `is_percentage` tinyint(1) NOT NULL DEFAULT '1',
  `is_overridable` tinyint(1) NOT NULL DEFAULT '0',
  `amount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_discounts_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_discounts_hist_discount_id` (`discount_id`) USING BTREE,
  KEY `idx_order_discounts_hist_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_dtls
-- ----------------------------
DROP TABLE IF EXISTS `order_dtls_hist`;
CREATE TABLE `order_dtls_hist` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `sale_item_id` bigint(20) NOT NULL,
  `sale_item_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `sale_item_hsn_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sub_class_id` bigint(20) NOT NULL,
  `sub_class_code` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `sub_class_hsn_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sub_class_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternative_name_to_print` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` decimal(14,5) DEFAULT NULL,
  `tray_weight` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tray_code` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uom_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uom_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `uom_symbol` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_calculation_method` tinyint(4) DEFAULT NULL,
  `is_open` tinyint(4) DEFAULT NULL,
  `is_combo_item` tinyint(4) NOT NULL,
  `fixed_price` decimal(14,5) DEFAULT NULL,
  `rtls_price` decimal(14,5) DEFAULT NULL,
  `whls_price` decimal(14,5) DEFAULT NULL,
  `is_whls_price_pc` tinyint(1) DEFAULT '0',
  `customer_price_variance` decimal(14,5) DEFAULT NULL,
  `tax_id` bigint(20) DEFAULT NULL,
  `tax_id_home_service` bigint(20) DEFAULT NULL,
  `tax_id_table_service` bigint(20) DEFAULT NULL,
  `tax_id_take_away_service` bigint(20) DEFAULT NULL,
  `tax_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_tax1_applied` tinyint(4) DEFAULT NULL,
  `tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax1_pc` decimal(14,5) DEFAULT NULL,
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `is_tax2_applied` tinyint(4) DEFAULT NULL,
  `tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax2_pc` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `is_tax3_applied` tinyint(4) DEFAULT NULL,
  `tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tax3_pc` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `is_gst_applied` tinyint(4) DEFAULT NULL,
  `gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gst_pc` decimal(14,5) DEFAULT NULL,
  `gst_amount` decimal(14,5) DEFAULT NULL,
  `is_tax1_included_in_gst` tinyint(4) DEFAULT NULL,
  `is_tax2_included_in_gst` tinyint(4) DEFAULT NULL,
  `is_tax3_included_in_gst` tinyint(4) DEFAULT NULL,
  `is_sc_included_in_gst` tinyint(4) DEFAULT NULL,
  `sc_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_sc_applied` tinyint(4) DEFAULT NULL,
  `sc_pc` decimal(14,5) DEFAULT NULL,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `item_total` decimal(14,5) DEFAULT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_id` bigint(20) NOT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(4) DEFAULT NULL,
  `discount_is_overridable` tinyint(4) DEFAULT NULL,
  `discount_is_item_specific` tinyint(4) DEFAULT NULL,
  `discount_permitted_for` tinyint(4) DEFAULT NULL,
  `discount_is_promotion` tinyint(4) DEFAULT '0',
  `discount_amount` decimal(14,5) NOT NULL,
  `discount_variants` decimal(14,5) DEFAULT NULL,
  `discount_grouping_quantity` decimal(14,5) NOT NULL,
  `discount_allow_editing` smallint(6) NOT NULL,
  `round_adjustment` decimal(14,5) DEFAULT NULL,
  `attrib1_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_options` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib1_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib2_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib3_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib4_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attrib5_selected_option` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_void` tinyint(4) DEFAULT '0',
  `cashier_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_time` datetime DEFAULT NULL,
  `serving_table_id` bigint(20) DEFAULT NULL,
  `served_by` bigint(20) DEFAULT NULL,
  `service_type` tinyint(1) DEFAULT NULL,
  `parent_dtl_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `has_choices` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `item_type` int(11) DEFAULT NULL,
  `sale_item_choices_id` int(11) DEFAULT NULL,
  `sale_item_choices_free_items` int(11) DEFAULT NULL,
  `sale_item_choices_max_items` int(11) DEFAULT NULL,
  `sale_item_choices_choice_id` int(11) DEFAULT NULL,
  `sale_item_choices_choice_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sale_item_choices_choice_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sale_item_choices_choice_description` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sale_item_choices_choice_is_global` smallint(1) DEFAULT NULL,
  `combo_content_id` bigint(20) DEFAULT NULL,
  `combo_content_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_content_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_content_description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `combo_content_max_items` int(11) DEFAULT NULL,
  `combo_content_uom_id` bigint(20) DEFAULT NULL,
  `seat_no` int(11) DEFAULT NULL,
  `is_printed_to_kitchen` tinyint(4) DEFAULT '0',
  `kitchen_status` tinyint(1) DEFAULT '0',
  `kitchen_print_status` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `kitchen_id` bigint(20) DEFAULT NULL,
  `void_by` bigint(20) DEFAULT NULL,
  `void_at` date DEFAULT NULL,
  `void_time` datetime DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_dtl_hist_parent_id` (`parent_dtl_id`) USING BTREE,
  KEY `idx_order_dtls_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_dtls_hist_sale_items_id` (`sale_item_id`) USING BTREE,
  KEY `idx_order_dtls_hist_item_type` (`item_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_hdrs
-- ----------------------------
DROP TABLE IF EXISTS `order_hdrs_hist`;
CREATE TABLE `order_hdrs_hist` (
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `order_no` int(11) DEFAULT NULL,
  `invoice_prefix` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `invoice_no` int(11) DEFAULT NULL,
  `shop_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `station_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL COMMENT 'POS open date',
  `order_time` datetime DEFAULT NULL COMMENT 'Current system date and time ',
  `shift_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `is_ar_customer` tinyint(1) DEFAULT NULL,
  `detail_total` decimal(14,5) DEFAULT NULL,
  `total_tax1` decimal(14,5) DEFAULT NULL,
  `total_tax2` decimal(14,5) DEFAULT NULL,
  `total_tax3` decimal(14,5) DEFAULT NULL,
  `total_gst` decimal(14,5) DEFAULT NULL,
  `total_sc` decimal(14,5) DEFAULT NULL,
  `total_detail_discount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `final_round_amount` decimal(14,5) DEFAULT NULL,
  `total_amount` decimal(14,5) DEFAULT NULL,
  `total_amount_paid` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `total_balance` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `actual_balance_paid` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_out` decimal(14,5) DEFAULT '0.00000',
  `remarks` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `closing_date` date DEFAULT NULL COMMENT 'POS closing date',
  `closing_time` datetime DEFAULT NULL COMMENT 'Current system date and time',
  `closed_by` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `void_by` bigint(20) DEFAULT NULL,
  `void_at` datetime DEFAULT NULL,
  `total_print_count` tinyint(4) DEFAULT NULL,
  `refund_total_tax1` decimal(14,5) DEFAULT '0.00000',
  `refund_total_tax2` decimal(14,5) DEFAULT '0.00000',
  `refund_total_tax3` decimal(14,5) DEFAULT '0.00000',
  `refund_total_gst` decimal(14,5) DEFAULT '0.00000',
  `refund_total_sc` decimal(14,5) DEFAULT '0.00000',
  `refund_amount` decimal(14,5) DEFAULT '0.00000',
  `due_datetime` datetime DEFAULT NULL,
  `order_medium` tinyint(4) DEFAULT NULL,
  `delivery_type` tinyint(1) DEFAULT NULL,
  `extra_charges` decimal(8,2) DEFAULT NULL,
  `extra_charge_tax_id` bigint(20) DEFAULT NULL,
  `extra_charge_tax_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax1_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax1_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax1_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax2_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax2_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax2_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax3_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_tax3_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_tax3_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_gst_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `extra_charge_gst_pc` decimal(14,5) DEFAULT NULL,
  `extra_charge_gst_amount` decimal(14,5) DEFAULT NULL,
  `extra_charge_remarks` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_message` longtext COLLATE utf8_unicode_ci,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `sync_status` tinyint(4) DEFAULT NULL,
  `bill_less_tax_amount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `bill_discount_amount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `bill_discount_percentage` decimal(8,2) NOT NULL DEFAULT '0.00',
  `serving_table_id` bigint(20) DEFAULT NULL,
  `served_by` bigint(20) DEFAULT NULL,
  `service_type` tinyint(1) DEFAULT NULL,
  `covers` int(11) DEFAULT NULL,
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_process_status` tinyint(4) DEFAULT '0',
  `is_locked` tinyint(1) DEFAULT '0',
  `locked_station_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `locked_station_id` bigint(20) DEFAULT NULL,
  `alias_text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_required_kitchen_print` tinyint(1) DEFAULT '0',
  `is_required_bill_print` tinyint(1) DEFAULT '0',
  `driver_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vehicle_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_ext_db_status` tinyint(4) DEFAULT '0',
  `sync_ext_db_msg` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `idx_order_hdrs_hist_locked_station_code` (`locked_station_code`) USING BTREE,
  KEY `idx_order_hdrs_hist_invoice_no` (`invoice_no`) USING BTREE,
  KEY `idx_order_hdrs_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_hdrs_hist_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_payment_hdr
-- ----------------------------
DROP TABLE IF EXISTS `order_payment_hdr_hist`;
CREATE TABLE `order_payment_hdr_hist` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `detail_total` decimal(14,5) DEFAULT NULL,
  `total_tax1` decimal(14,5) DEFAULT NULL,
  `total_tax2` decimal(14,5) DEFAULT NULL,
  `total_tax3` decimal(14,5) DEFAULT NULL,
  `total_gst` decimal(14,5) DEFAULT NULL,
  `total_sc` decimal(14,5) DEFAULT NULL,
  `total_detail_discount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `final_round_amount` decimal(14,5) DEFAULT NULL,
  `total_amount` decimal(14,5) DEFAULT NULL,
  `total_amount_paid` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `total_balance` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `actual_balance_paid` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_out` decimal(14,5) DEFAULT '0.00000',
  `bill_less_tax_amount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `bill_discount_amount` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `is_refund` tinyint(1) NOT NULL DEFAULT '0',
  `is_advance` tinyint(1) DEFAULT NULL,
  `is_final` tinyint(1) NOT NULL DEFAULT '0',
  `remarks` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_payment_hdr_hist_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_payments
-- ----------------------------
DROP TABLE IF EXISTS `order_payments_hist`;
CREATE TABLE `order_payments_hist` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_payment_hdr_id` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_mode` tinyint(2) DEFAULT '1' COMMENT 'Cash(0,"Pay By Cash"),Card(1,"Pay By Card"),Coupon(2,"Pay By Voucher"), Company(3,"Pay By Company"),Discount(4,"Discount"),Balance(5,"Balance"),Repay(6,"Re-Payment"),Cash10(100,"Pay By Cash 10"),Cash20(101,"Pay By Cash 20");',
  `paid_amount` decimal(14,5) DEFAULT NULL,
  `card_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_no` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name_on_card` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_expiry_month` bigint(20) DEFAULT NULL,
  `card_expiry_year` decimal(4,0) DEFAULT NULL,
  `card_approval_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_account_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pos_customer_receipt` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pos_merchant_receipt` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  `voucher_value` decimal(14,5) DEFAULT NULL,
  `voucher_count` tinyint(2) DEFAULT NULL,
  `cashier_id` bigint(20) DEFAULT NULL,
  `cashier_shift_id` bigint(20) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `discount_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount_price` decimal(14,5) DEFAULT NULL,
  `discount_is_percentage` tinyint(2) DEFAULT NULL,
  `discount_is_overridable` tinyint(2) DEFAULT NULL,
  `discount_amount` decimal(14,5) DEFAULT NULL,
  `is_repayment` tinyint(1) NOT NULL DEFAULT '0',
  `is_voucher_balance_returned` tinyint(4) DEFAULT '0',
  `partial_balance` decimal(14,5) DEFAULT '0.00000',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_payments_hist_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_pre_discounts
-- ----------------------------
DROP TABLE IF EXISTS `order_pre_discounts_hist`;
CREATE TABLE `order_pre_discounts_hist` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `discount_id` bigint(20) NOT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(8,2) NOT NULL DEFAULT '0.00',
  `is_percentage` tinyint(1) NOT NULL DEFAULT '1',
  `is_overridable` tinyint(1) NOT NULL DEFAULT '0',
  `last_sync_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_pre_discounts_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_pre_discounts_hist_discount_id` (`discount_id`) USING BTREE,
  KEY `idx_order_pre_discounts_hist_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_queue
-- ----------------------------
DROP TABLE IF EXISTS `order_queue_hist`;
CREATE TABLE `order_queue_hist` (
  `id` int(11) NOT NULL ,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_order_queue_hist_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_refunds
-- ----------------------------
DROP TABLE IF EXISTS `order_refunds_hist`;
CREATE TABLE `order_refunds_hist` (
  `id` int(11) NOT NULL ,
  `order_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_dtl_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_payment_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` decimal(14,5) DEFAULT NULL,
  `paid_amount` decimal(14,5) DEFAULT NULL COMMENT 'Tax Included',
  `tax1_amount` decimal(14,5) DEFAULT NULL,
  `tax2_amount` decimal(14,5) DEFAULT NULL,
  `tax3_amount` decimal(14,5) DEFAULT NULL,
  `sc_amount` decimal(14,5) DEFAULT NULL,
  `gst_amount` decimal(14,5) DEFAULT NULL,
  `refund_date` date DEFAULT NULL,
  `refund_time` datetime DEFAULT NULL,
  `refunded_by` int(11) DEFAULT NULL,
  `sync_message` longtext COLLATE utf8_unicode_ci,
  `sync_status` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_refunds_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_refunds_hist_order_dtl_id` (`order_dtl_id`) USING BTREE,
  KEY `idx_order_refunds_hist_order_payment_id` (`order_payment_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_serving_seats
-- ----------------------------
DROP TABLE IF EXISTS `order_serving_seats_hist`;
CREATE TABLE `order_serving_seats_hist` (
  `id` bigint(20) NOT NULL  ,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `table_id` int(11) NOT NULL,
  `seat_no` int(11) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_void` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_order_serving_seats_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_serving_seats_hist_table_id` (`table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_serving_tables
-- ----------------------------
DROP TABLE IF EXISTS `order_serving_tables_hist`;
CREATE TABLE `order_serving_tables_hist` (
  `id` bigint(20) NOT NULL  ,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `table_id` bigint(20) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `selected_seat_no` int(11) DEFAULT NULL,
  `is_selected` tinyint(1) DEFAULT '1',
  `is_void` int(11) DEFAULT '0',
  `covers` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_order_serving_tables_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_serving_tables_hist_table_id` (`table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_split_dtls
-- ----------------------------
DROP TABLE IF EXISTS `order_split_dtls_hist`;
CREATE TABLE `order_split_dtls_hist` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `split_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_sub_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_dtl_qty` decimal(14,5) DEFAULT NULL,
  `order_dtl_price` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_split_dtls_hist_order_id` (`order_id`) USING BTREE,
  KEY `idx_order_split_dtls_hist_order_dtl_id` (`order_dtl_id`) USING BTREE,
  KEY `idx_order_split_dtls_hist_split_id` (`split_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for order_splits
-- ----------------------------
DROP TABLE IF EXISTS `order_splits_hist`;
CREATE TABLE `order_splits_hist` (
  `id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `order_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `split_no` int(11) DEFAULT NULL,
  `based_on` tinyint(4) NOT NULL DEFAULT '0',
  `value` decimal(14,5) DEFAULT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(14,5) DEFAULT NULL,
  `adj_amount` decimal(14,5) DEFAULT NULL,
  `actual_amount_paid` decimal(14,5) DEFAULT NULL,
  `discount` decimal(14,5) DEFAULT NULL,
  `round_adj` decimal(14,5) DEFAULT NULL,
  `part_pay_adj` decimal(14,5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_splits_hist_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shift_summary
-- ----------------------------
DROP TABLE IF EXISTS `shift_summary_hist`;
CREATE TABLE `shift_summary_hist` (
  `auto_id` int(11) NOT NULL  ,
  `shift_id` int(11) NOT NULL,
  `shift_by` int(11) NOT NULL,
  `station_code` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `opening_date` date NOT NULL COMMENT 'pos open  date',
  `opening_time` datetime NOT NULL,
  `closing_date` datetime NOT NULL,
  `closing_time` datetime NOT NULL,
  `opening_float` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_receipts` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `card_receipts` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `card_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `voucher_receipts` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `voucher_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `online_receipts` decimal(8,2) NOT NULL DEFAULT '0.00',
  `online_receipts_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `accounts_receivable` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_returned` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `voucher_balance` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_out` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_refund` decimal(14,5) DEFAULT '0.00000',
  `card_refund` decimal(14,5) DEFAULT '0.00000',
  `voucher_refund` decimal(14,5) DEFAULT '0.00000',
  `online_refund` decimal(14,5) DEFAULT '0.00000',
  `accounts_refund` decimal(14,5) DEFAULT '0.00000',
  `total_refund` decimal(14,5) DEFAULT '0.00000',
  `sales` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `previous_advance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `daily_cashout` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `net_cash_received` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `voucher_balance_returned` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `closing_cash` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `actual_cash` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_variance` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_deposit` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `cash_remaining` decimal(14,5) NOT NULL DEFAULT '0.00000',
  `referance_number` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sync_status` tinyint(2) NOT NULL DEFAULT '0',
  `sync_message` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`auto_id`,`station_code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `txn_cashouts_hist`;
CREATE TABLE `txn_cashouts_hist` (
  `id` int(11) NOT NULL  ,
  `station_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `shift_id` bigint(20) NOT NULL,
  `cashout_date` date NOT NULL,
  `cashout_time` datetime NOT NULL,
  `title` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(14,5) NOT NULL,
  `remarks` text COLLATE utf8_unicode_ci,
  `created_by` bigint(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` bigint(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `sync_message` longtext COLLATE utf8_unicode_ci,
  `sync_status` tinyint(4) DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`station_id`),
  KEY `idx_txn_cashouts_hist_title` (`title`) USING BTREE,
  KEY `idx_txn_cashouts_hist_station_id` (`station_id`) USING BTREE,
  KEY `idx_txn_cashouts_hist_user_id` (`user_id`) USING BTREE,
  KEY `idx_txn_cashouts_hist_shift_id` (`shift_id`) USING BTREE
)   DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `modules`;
CREATE TABLE `modules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_enable` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for stock_request_hdr
-- ----------------------------
DROP TABLE IF EXISTS `stock_request_hdr`;
CREATE TABLE `stock_request_hdr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `source_shop_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `destination_shop_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `request_date` date NULL DEFAULT NULL COMMENT 'POS open date',
  `request_time` datetime(0) NULL DEFAULT NULL COMMENT 'Current system date and time ',
  `exp_delivery_date` date NULL DEFAULT NULL COMMENT 'POS closing date',
  `exp_delivery_time` datetime(0) NULL DEFAULT NULL COMMENT 'Current system date and time',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '0-PENDING, 1-APPROCED, 2-Cancelled',
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `type` tinyint(4) UNSIGNED NULL DEFAULT 0 COMMENT '0-request send, 1-incoming request',
  `other_area` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `area_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status_request_from` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stock_request_hdr_id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for stock_request_dtl
-- ----------------------------
DROP TABLE IF EXISTS `stock_request_dtl`;
CREATE TABLE `stock_request_dtl`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_request_hdr_id` bigint(20) NOT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `uom_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `request_qty` decimal(14, 5) NULL DEFAULT 0.00000,
  `item_status` tinyint(4) NULL DEFAULT 0 COMMENT '0-PENDING, 1-APPROCED, 2-Cancelled',
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `remarks` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `removed_status` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stock_request_dtl_id`(`id`) USING BTREE,
  INDEX `idx_stock_request_dtl_stock_request_hdr_id`(`stock_request_hdr_id`) USING BTREE,
  INDEX `idx_stock_request_dtl_stock_item_id`(`stock_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stock_return_dtl
-- ----------------------------
DROP TABLE IF EXISTS `stock_return_dtl`;
CREATE TABLE `stock_return_dtl`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_return_hdr_id` bigint(20) NOT NULL,
  `ref_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `uom_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `return_qty` int(11) NOT NULL,
  `item_status` tinyint(4) NOT NULL,
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `remarks` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stock_return_hdr
-- ----------------------------
DROP TABLE IF EXISTS `stock_return_hdr`;
CREATE TABLE `stock_return_hdr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `return_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `source_shop_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `destination_shop_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `return_date` date NOT NULL,
  `return_time` datetime(0) NOT NULL,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `updated_at` datetime(0) NOT NULL,
  `is_other_area` tinyint(4) NULL DEFAULT NULL,
  `area_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for stock_return_dtl
-- ----------------------------
DROP TABLE IF EXISTS `stock_return_dtl`;
CREATE TABLE `stock_return_dtl`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_return_hdr_id` bigint(20) NOT NULL,
  `ref_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `uom_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `return_qty` int(11) NOT NULL,
  `item_status` tinyint(4) NOT NULL,
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `remarks` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stock_transfer_dtl
-- ----------------------------
DROP TABLE IF EXISTS `stock_transfer_dtl`;
CREATE TABLE `stock_transfer_dtl`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_transfer_hdr_id` bigint(20) NOT NULL,
  `ref_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `stock_item_id` bigint(20) NOT NULL,
  `stock_item_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `uom_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `transfer_qty` decimal(14, 5) NULL DEFAULT 0.00000,
  `item_status` tinyint(4) NULL DEFAULT 0 COMMENT '0-PENDING, 1-APPROCED, 2-Cancelled',
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `remarks` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stock_transfer_dtl_stock_transfer_hdr_id`(`stock_transfer_hdr_id`) USING BTREE,
  INDEX `idx_stock_transfer_dtl_stock_item_id`(`stock_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stock_transfer_hdr
-- ----------------------------
DROP TABLE IF EXISTS `stock_transfer_hdr`;
CREATE TABLE `stock_transfer_hdr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transfer_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `source_shop_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `destination_shop_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `transfer_date` date NULL DEFAULT NULL COMMENT 'POS open date',
  `transfer_time` datetime(0) NULL DEFAULT NULL COMMENT 'Current system date and time ',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '0-PENDING, 1-APPROCED, 2-Cancelled',
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_stock_transfer_hdr_id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- View structure for v_location_vise_report
-- ----------------------------
DROP VIEW IF EXISTS `v_location_vise_report`;
CREATE  VIEW `v_location_vise_report` AS select `order_hdrs`.`order_id` AS `order_id`,`order_hdrs`.`closing_date` AS `closing_date`,`order_hdrs`.`user_id` AS `user_id`,`order_hdrs`.`shift_id` AS `shift_id`,`item_classes`.`super_class_id` AS `super_class_id`,`super_class`.`name` AS `super_class_name`,`order_dtls`.`sub_class_id` AS `sub_class_id`,`order_dtls`.`sub_class_name` AS `sub_class_name`,`order_dtls`.`sale_item_id` AS `sale_item_id`,`order_dtls`.`sale_item_code` AS `sale_item_code`,`order_dtls`.`name` AS `sale_item_name`,`order_dtls`.`qty` AS `qty`,`order_dtls`.`tax_calculation_method` AS `tax_calculation_method`,`order_dtls`.`fixed_price` AS `fixed_price`,`order_dtls`.`tax_id` AS `tax_id`,`order_dtls`.`tax_name` AS `tax_name`,`order_dtls`.`tax1_name` AS `tax1_name`,`order_dtls`.`tax1_amount` AS `tax1_amount`,`order_dtls`.`tax2_name` AS `tax2_name`,`order_dtls`.`tax2_amount` AS `tax2_amount`,`order_dtls`.`tax3_name` AS `tax3_name`,`order_dtls`.`tax3_amount` AS `tax3_amount`,`order_dtls`.`gst_name` AS `gst_name`,`order_dtls`.`gst_amount` AS `gst_amount`,`order_dtls`.`sc_name` AS `sc_name`,`order_dtls`.`sc_amount` AS `sc_amount`,`order_hdrs`.`bill_discount_percentage` AS `bill_discount_percentage`,`order_dtls`.`is_void` AS `is_void`,`order_hdrs`.`status` AS `order_status`,`order_hdrs`.`final_round_amount` AS `final_round_amount`,`order_dtls`.`item_total` AS `item_total`,((`order_dtls`.`item_total` - ((((`order_dtls`.`tax1_amount` + `order_dtls`.`tax2_amount`) + `order_dtls`.`tax3_amount`) + `order_dtls`.`gst_amount`) + `order_dtls`.`sc_amount`)) - (((`order_dtls`.`item_total` - ((((`order_dtls`.`tax1_amount` + `order_dtls`.`tax2_amount`) + `order_dtls`.`tax3_amount`) + `order_dtls`.`gst_amount`) + `order_dtls`.`sc_amount`)) * `order_hdrs`.`bill_discount_percentage`) / 100)) AS `taxable_amount`,`order_hdrs`.`closed_by` AS `closed_by`,`users`.`name` AS `cashier_name`,`order_hdrs`.`served_by` AS `served_by`,`employees`.`f_name` AS `waiter_name`,`order_hdrs`.`invoice_prefix` AS `invoice_prefix`,`pos_invoice`.`id` AS `invoice_no`,ucase(ifnull(`serving_table_location`.`name`,'Counter')) AS `location_name`,`serving_tables`.`serving_table_location_id` AS `serving_table_location_id`,`order_refunds`.`qty` AS `refund_qty`,`order_refunds`.`paid_amount` AS `refund_paid_amount`,`order_refunds`.`tax1_amount` AS `refund_tax1_amount`,`order_refunds`.`tax2_amount` AS `refund_tax2_amount`,`order_refunds`.`tax3_amount` AS `refund_tax3_amount`,`order_refunds`.`sc_amount` AS `refund_sc_amount`,`uoms`.`uom_symbol` AS `uom_symbol`,`uoms`.`decimal_places` AS `uom_decimal_places`,(`order_refunds`.`paid_amount` - (((`order_refunds`.`tax1_amount` + `order_refunds`.`tax2_amount`) + `order_refunds`.`tax3_amount`) + `order_refunds`.`sc_amount`)) AS `taxable_refund_paid_amount` from ((((((((((`order_hdrs` join `order_dtls` on((`order_hdrs`.`order_id` = `order_dtls`.`order_id`))) left join `serving_tables` on((`order_dtls`.`serving_table_id` = `serving_tables`.`id`))) left join `serving_table_location` on((`serving_tables`.`serving_table_location_id` = `serving_table_location`.`id`))) join `item_classes` on((`order_dtls`.`sub_class_id` = `item_classes`.`id`))) join `item_classes` `super_class` on((`item_classes`.`super_class_id` = `super_class`.`id`))) left join `users` on((`order_hdrs`.`closed_by` = `users`.`id`))) left join `employees` on((`order_hdrs`.`served_by` = `employees`.`id`))) join `pos_invoice` on((`order_hdrs`.`order_id` = `pos_invoice`.`order_id`))) left join `order_refunds` on((`order_dtls`.`id` = `order_refunds`.`order_dtl_id`))) join `uoms` on((`order_dtls`.`uom_code` = `uoms`.`code`))) where ((`order_dtls`.`is_void` = 0) and ((`order_hdrs`.`status` = 3) or (`order_hdrs`.`status` = 4)));

-- ----------------------------
-- View structure for v_customer_list
-- ----------------------------
DROP VIEW IF EXISTS `v_customer_list`;
CREATE  VIEW `v_customer_list` AS 

select 
`cust`.`id` AS `id`,
`cust`.`code` AS `code`,
`cust`.`name` AS `NAME`,
`cust`.`address` AS `address`,
`cust`.`address2` AS `address2`,
`cust`.`address3` AS `address3`,
`cust`.`address4` AS `address4`,
`cust`.`city` AS `city`,`cust`.`state` AS `state`,
`cust`.`state_code` AS `state_code`,
`cust`.`country` AS `country`,
`cust`.`phone` AS `phone`,
`cust`.`phone2` AS `phone2`,
`cust`.`tin` AS `tin`,
`cust`.`gst_party_type` AS `gst_party_type`,
`cust`.`gst_reg_type` AS `gst_reg_type`,
`cust`.`customer_type` AS `customer_type`,
`cust`.`is_ar` AS `is_ar` 
from `customers` `cust` 
where ((`cust`.`is_valid` = 1) and (`cust`.`id` <> 0) and (`cust`.`is_deleted` = 0)) 
union 
select `hdr`.`customer_id` AS `id`,
`ordcust`.`code` AS `code`,
`ordcust`.`name` AS `NAME`,
`ordcust`.`address` AS `address`,
`ordcust`.`address2` AS `address2`,
`ordcust`.`address3` AS `address3`,
`ordcust`.`address4` AS `address4`,
`ordcust`.`city` AS `city`,
`ordcust`.`state` AS `state`,
`ordcust`.`state_code` AS `state_code`,
`ordcust`.`country` AS `country`,
`ordcust`.`phone` AS `phone`,
`ordcust`.`phone2` AS `phone2`,
`ordcust`.`tin` AS `tin`,
`ordcust`.`gst_party_type` AS `gst_party_type`,
`ordcust`.`gst_reg_type` AS `gst_reg_type`,
`ordcust`.`customer_type` AS `customer_type`,
`ordcust`.`is_ar` AS `is_ar` 
from 
(`order_customers` `ordcust` 
join `order_hdrs` `hdr` on((`ordcust`.`order_id` = `hdr`.`order_id`))) 
where ((not(`hdr`.`customer_id` in (select `customers`.`id` from `customers` where (`customers`.`id` <> 0)))) and (`ordcust`.`name` <> 'Walk-in'));

-- ----------------------------
-- View structure for v_txn_cashouts
-- ----------------------------
DROP VIEW IF EXISTS `v_txn_cashouts`;
CREATE  VIEW `v_txn_cashouts` AS 
SELECT
txn_cashouts.id AS id,
txn_cashouts.station_id AS station_id,
txn_cashouts.user_id AS user_id,
txn_cashouts.shift_id AS shift_id,
txn_cashouts.cashout_date AS cashout_date,
txn_cashouts.cashout_time AS cashout_time,
txn_cashouts.amount AS amount,
txn_cashouts.remarks AS remarks,
txn_cashouts.created_by AS created_by,
txn_cashouts.created_at AS created_at,
txn_cashouts.updated_by AS updated_by,
txn_cashouts.updated_at AS updated_at,
txn_cashouts.sync_message AS sync_message,
txn_cashouts.sync_status AS sync_status,
txn_cashouts.is_deleted AS is_deleted,
txn_cashouts.title
FROM
	`txn_cashouts`
WHERE
	(
		`txn_cashouts`.`is_deleted` = 0
	);
	
-- ----------------------------
-- View structure for v_stock_in_out_preview
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_in_out_preview`;
CREATE VIEW `v_stock_in_out_preview` AS 
SELECT
	`stock_in_dtl`.`id` AS `id`,
	`stock_in_dtl`.`stock_in_hdr_id` AS `stock_in_hdr_id`,
	`stock_in_dtl`.`stock_item_id` AS `stock_item_id`,
	`stock_in_dtl`.`description` AS `description`,
	`stock_in_hdr`.`date` AS `date`,
	`stock_in_hdr`.`type` AS `type`,
	(
		CASE `stock_in_hdr`.`type`
		WHEN 3 THEN
			(
				- (1) * `stock_in_dtl`.`qty_received`
			)
		WHEN 4 THEN
			(
				- (1) * `stock_in_dtl`.`qty_received`
			)
		ELSE
			`stock_in_dtl`.`qty_received`
		END
	) AS `qty_received`
FROM
	(
		`stock_in_hdr`
		LEFT JOIN `stock_in_dtl` ON (
			(
				`stock_in_dtl`.`stock_in_hdr_id` = `stock_in_hdr`.`id`
			)
		)
	);
	
-- ----------------------------
-- View structure for v_stock_in_out
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_in_out`;
CREATE VIEW v_stock_in_out AS
SELECT
	`stock_in_hdr`.`date` AS `date`,
	`v_stock_in_out_preview`.`stock_item_id` AS `stock_item_id`,
	`stock_items`.`name` AS `stock_name`,
	(
		SELECT
			ifnull(
				sum(`dtl`.`qty_received`),
				0
			)
		FROM
			(
				`v_stock_in_out_preview` `dtl`
				JOIN `stock_in_hdr` `hdr` ON (
					(
						`dtl`.`stock_in_hdr_id` = `hdr`.`id`
					)
				)
			)
		WHERE
			(
				(
					`hdr`.`date` < `stock_in_hdr`.`date`
				)
				AND (
					`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
				)
			)
	) AS `opening_stock`,
	(
		SELECT
			ifnull(
				sum(`dtl`.`qty_received`),
				0
			)
		FROM
			(
				`v_stock_in_out_preview` `dtl`
				JOIN `stock_in_hdr` `hdr` ON (
					(
						`dtl`.`stock_in_hdr_id` = `hdr`.`id`
					)
				)
			)
		WHERE
			(
				(`hdr`.`type` = 1)
				AND (
					`hdr`.`date` = `stock_in_hdr`.`date`
				)
				AND (
					`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
				)
			)
	) AS `stock_in`,
	(
		SELECT
			ifnull(
				sum(`dtl`.`qty_received`),
				0
			)
		FROM
			(
				`v_stock_in_out_preview` `dtl`
				JOIN `stock_in_hdr` `hdr` ON (
					(
						`dtl`.`stock_in_hdr_id` = `hdr`.`id`
					)
				)
			)
		WHERE
			(
				(`hdr`.`type` = 2)
				AND (
					`hdr`.`date` = `stock_in_hdr`.`date`
				)
				AND (
					`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
				)
			)
	) AS `adjustment`,
	(
		SELECT
			ifnull(
				sum(`dtl`.`qty_received`),
				0
			)
		FROM
			(
				`stock_in_dtl` `dtl`
				JOIN `stock_in_hdr` `hdr` ON (
					(
						`dtl`.`stock_in_hdr_id` = `hdr`.`id`
					)
				)
			)
		WHERE
			(
				(`hdr`.`type` = 3)
				AND (
					`hdr`.`date` = `stock_in_hdr`.`date`
				)
				AND (
					`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
				)
			)
	) AS `disposal`,
	(
		SELECT
			ifnull(
				sum(`dtl`.`qty_received`),
				0
			)
		FROM
			(
				`stock_in_dtl` `dtl`
				JOIN `stock_in_hdr` `hdr` ON (
					(
						`dtl`.`stock_in_hdr_id` = `hdr`.`id`
					)
				)
			)
		WHERE
			(
				(`hdr`.`type` = 4)
				AND (
					`hdr`.`date` = `stock_in_hdr`.`date`
				)
				AND (
					`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
				)
			)
	) AS `sales`,

IF (
	(
		(
			SELECT
				ifnull(
					sum(`dtl`.`qty_received`),
					0
				)
			FROM
				(
					`v_stock_in_out_preview` `dtl`
					JOIN `stock_in_hdr` `hdr` ON (
						(
							`dtl`.`stock_in_hdr_id` = `hdr`.`id`
						)
					)
				)
			WHERE
				(
					(
						`hdr`.`date` < `stock_in_hdr`.`date`
					)
					AND (
						`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
					)
				)
		) <> ''
	),
	(
		(
			SELECT
				ifnull(
					sum(`testdtl`.`qty_received`),
					0
				)
			FROM
				(
					`v_stock_in_out_preview` `testdtl`
					JOIN `stock_in_hdr` `hdr` ON (
						(
							`testdtl`.`stock_in_hdr_id` = `hdr`.`id`
						)
					)
				)
			WHERE
				(
					(
						`hdr`.`date` = `stock_in_hdr`.`date`
					)
					AND (
						`testdtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
					)
				)
		) + (
			SELECT
				sum(`dtl`.`qty_received`)
			FROM
				(
					`v_stock_in_out_preview` `dtl`
					JOIN `stock_in_hdr` `hdr` ON (
						(
							`dtl`.`stock_in_hdr_id` = `hdr`.`id`
						)
					)
				)
			WHERE
				(
					(
						`hdr`.`date` < `stock_in_hdr`.`date`
					)
					AND (
						`dtl`.`stock_item_id` = `v_stock_in_out_preview`.`stock_item_id`
					)
				)
		)
	),
	sum(
		`v_stock_in_out_preview`.`qty_received`
	)
) AS `current_stock`
FROM
	(
		(
			`stock_in_hdr`
			JOIN `v_stock_in_out_preview` ON (
				(
					`v_stock_in_out_preview`.`stock_in_hdr_id` = `stock_in_hdr`.`id`
				)
			)
		)
		LEFT JOIN `stock_items` ON (
			(
				`stock_items`.`id` = `v_stock_in_out_preview`.`stock_item_id`
			)
		)
	)
WHERE
	(
		(1 = 1)
		AND (
			`stock_items`.`is_deleted` = 0
		)
	)
GROUP BY
	`v_stock_in_out_preview`.`stock_item_id`,
	`stock_in_hdr`.`date`
ORDER BY
	`stock_in_hdr`.`date` DESC;
	
-- ----------------------------
-- View structure for v_stock_return
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_return`;
CREATE  VIEW `v_stock_return` AS select `stock_return_hdr`.`id` AS `id`,`stock_return_hdr`.`return_no` AS `return_no`,`stock_return_hdr`.`source_shop_code` AS `source_shop_code`,`stock_return_hdr`.`destination_shop_code` AS `destination_shop_code`,`stock_return_hdr`.`return_date` AS `return_date`,`stock_return_hdr`.`return_time` AS `return_time`,`stock_return_hdr`.`remarks` AS `remarks`,`stock_return_hdr`.`status` AS `status`,`stock_return_hdr`.`is_deleted` AS `is_deleted`,`stock_return_hdr`.`created_by` AS `created_by`,`stock_return_hdr`.`created_at` AS `created_at`,`stock_return_hdr`.`updated_by` AS `updated_by`,`stock_return_hdr`.`updated_at` AS `updated_at`,`stock_return_hdr`.`is_other_area` AS `is_other_area`,`stock_return_hdr`.`area_id` AS `area_id`,if(isnull(`shop`.`id`),'incoming','outgoing') AS `type` from (`stock_return_hdr` left join `shop` on((`stock_return_hdr`.`source_shop_code` = `shop`.`code`)));

-- ----------------------------
-- View structure for v_currencies
-- ----------------------------
DROP VIEW IF EXISTS `v_currencies`;
CREATE VIEW `v_currencies` AS select `currencies`.*  from `currencies` where (`currencies`.`is_deleted` = '0');

-- ----------------------------
-- View structure for v_ar_companies
-- ----------------------------
DROP VIEW IF EXISTS `v_ar_companies`;
CREATE VIEW `v_ar_companies` AS
SELECT
customers.*
from `customers`
where ((`customers`.`is_valid` = 1) and (`customers`.`is_system` = 0) and (`customers`.`is_ar` = 1) and (`customers`.`is_deleted` = 0));

-- ----------------------------
-- View structure for v_card_numbers
-- ----------------------------
DROP VIEW IF EXISTS `v_card_numbers`;
CREATE VIEW `v_card_numbers` AS select NULL AS `employee_id`,`users`.`id` AS `user_id`,`users`.`card_no` AS `card_no` from `users` where (`users`.`employee_id` IS NULL) union select `employees`.`id` AS `employee_id`,NULL AS `user_id`,`employees`.`card_no` AS `card_no` from `employees` ;

-- ----------------------------
-- View structure for v_users
-- ----------------------------
DROP VIEW IF EXISTS `v_users`;
CREATE VIEW `v_users` AS select `users`.`id` AS `id`,`users`.`code` AS `code`,`users`.`name` AS `name`,`users`.`card_no` AS `card_no`,`users`.`user_group_id` AS `user_group_id`,`users`.`employee_id` AS `employee_id`,`users`.`password` AS `password`,`users`.`valid_from` AS `valid_from`,`users`.`valid_to` AS `valid_to`,`users`.`is_active` AS `is_active`,`users`.`is_admin` AS `is_admin`,`users`.`email` AS `email`,`users`.`lastlogin_date` AS `lastlogin_date`,`users`.`created_by` AS `created_by`,`users`.`created_at` AS `created_at`,`users`.`updated_by` AS `updated_by`,`users`.`updated_at` AS `updated_at`,`users`.`publish_status` AS `publish_status`,`users`.`is_deleted` AS `is_deleted`,`users`.`is_synchable` AS `is_synchable` from `users` where (`users`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_cashiers
-- ----------------------------
DROP VIEW IF EXISTS `v_cashiers`;
CREATE VIEW `v_cashiers` AS select `v_users`.`id` AS `id`,`v_users`.`code` AS `code`,`v_users`.`name` AS `name`,`v_users`.`card_no` AS `card_no`,`v_users`.`user_group_id` AS `user_group_id`,`v_users`.`employee_id` AS `employee_id`,`v_users`.`password` AS `password`,`v_users`.`valid_from` AS `valid_from`,`v_users`.`valid_to` AS `valid_to`,`v_users`.`is_active` AS `is_active`,`v_users`.`is_admin` AS `is_admin`,`v_users`.`email` AS `email`,`v_users`.`lastlogin_date` AS `lastlogin_date`,`v_users`.`created_by` AS `created_by`,`v_users`.`created_at` AS `created_at`,`v_users`.`updated_by` AS `updated_by`,`v_users`.`updated_at` AS `updated_at`,`v_users`.`publish_status` AS `publish_status`,`v_users`.`is_deleted` AS `is_deleted`,`v_users`.`is_synchable` AS `is_synchable` from `v_users` ;

-- ----------------------------
-- View structure for v_choices
-- ----------------------------
DROP VIEW IF EXISTS `v_choices`;
CREATE VIEW `v_choices` AS select `choices`.`id` AS `id`,`choices`.`code` AS `code`,`choices`.`name` AS `name`,`choices`.`description` AS `description`,`choices`.`is_global` AS `is_global`,`choices`.`created_by` AS `created_by`,`choices`.`created_at` AS `created_at`,`choices`.`updated_by` AS `updated_by`,`choices`.`updated_at` AS `updated_at`,`choices`.`publish_status` AS `publish_status`,`choices`.`is_deleted` AS `is_deleted`,`choices`.`is_synchable` AS `is_synchable` from `choices` where (`choices`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_customer_types
-- ----------------------------
DROP VIEW IF EXISTS `v_customer_types`;
CREATE VIEW `v_customer_types` AS select * from `customer_types` where (`customer_types`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_customers
-- ----------------------------
DROP VIEW IF EXISTS `v_customers`;
CREATE VIEW `v_customers` AS SELECT
	`customers`.*
FROM
	`customers`
WHERE
	(
		(`customers`.`is_valid` = 1)
		AND (`customers`.`is_deleted` = 0)
	);

-- ----------------------------
-- View structure for v_sale_items
-- ----------------------------
DROP VIEW IF EXISTS `v_sale_items`;
CREATE VIEW `v_sale_items` AS 
SELECT
si.id AS id,
si.stock_item_id AS stock_item_id,
si.stock_item_code AS stock_item_code,
si.`code` AS `code`,
si.`hsn_code` as hsn_code,
si.is_group_item AS is_group_item,
si.group_item_id AS group_item_id,
si.is_combo_item AS is_combo_item,
si.`name` AS `name`,
si.description AS description,
si.sub_class_id AS sub_class_id,
si.is_valid AS is_valid,
si.alternative_name AS alternative_name,
si.is_printable_to_kitchen AS is_printable_to_kitchen,
si.name_to_print AS name_to_print,
si.alternative_name_to_print AS alternative_name_to_print,
si.barcode AS barcode,
si.is_open AS is_open,
si.qty_on_hand AS qty_on_hand,
si.uom_id AS uom_id,
si.tax_id AS tax_id,
si.fixed_price AS fixed_price,
CASE WHEN si.whls_price IS NULL OR si.whls_price=0 THEN si.fixed_price ELSE si.whls_price END AS whls_price,
si.is_whls_price_pc,
si.item_cost AS item_cost,
si.tax_calculation_method AS tax_calculation_method,
si.is_require_weighing AS is_require_weighing,
si.display_order AS display_order,
si.is_hot_item_1 AS is_hot_item_1,
si.hot_item_1_display_order AS hot_item_1_display_order,
si.is_hot_item_2 AS is_hot_item_2,
si.hot_item_2_display_order AS hot_item_2_display_order,
si.is_hot_item_3 AS is_hot_item_3,
si.hot_item_3_display_order AS hot_item_3_display_order,
si.fg_color AS fg_color,
si.bg_color AS bg_color,
si.attrib1_name AS attrib1_name,
si.attrib1_options AS attrib1_options,
si.attrib2_name AS attrib2_name,
si.attrib2_options AS attrib2_options,
si.attrib3_name AS attrib3_name,
si.attrib3_options AS attrib3_options,
si.attrib4_name AS attrib4_name,
si.attrib4_options AS attrib4_options,
si.attrib5_name AS attrib5_name,
si.attrib5_options AS attrib5_options,
si.tag1 AS tag1,
si.tag2 AS tag2,
si.tag3 AS tag3,
si.tag4 AS tag4,
si.tag5 AS tag5,
si.choice_ids AS choice_ids,
si.item_thumb AS item_thumb,
si.created_by AS created_by,
si.created_at AS created_at,
si.updated_by AS updated_by,
si.updated_at AS updated_at,
si.publish_status AS publish_status,
si.is_deleted AS is_deleted,
si.is_synchable AS is_synchable,
item_classes.`code` AS sub_class_code,
item_classes.`name` AS sub_class_name,
ifnull(item_classes.print_order,9999) AS print_order,
si.taxation_based_on,
si.tax_id_home_service,
si.tax_id_table_service,
si.tax_id_take_away_service,
si.best_before,
sale_item_ext.kitchen_id
FROM
(sale_items AS si
JOIN item_classes ON ((si.sub_class_id = item_classes.id)))
LEFT JOIN sale_item_ext ON si.id = sale_item_ext.id
where ((`si`.`is_deleted` = 0) and `si`.`is_valid`);

-- ----------------------------
-- View structure for v_customers_type_item_prices
-- ----------------------------
DROP VIEW IF EXISTS `v_customers_type_item_prices`;
CREATE VIEW `v_customers_type_item_prices` AS 
SELECT
      ctip.customer_type_id AS customer_type_id,
      ct.`name` AS customer_type_name,
      si.id AS sale_item_id,
      si.`name` AS sale_item_name,
      si.fixed_price AS fixed_price,
      ctip.price_variance_pc AS price_variance_pc,
      ctip.is_percentage AS is_percentage,
      ctip.is_price_variance
from ((`v_sale_items` `si` join `customers_type_item_prices` `ctip` on((`si`.`id` = `ctip`.`sale_item_id`))) left join `v_customer_types` `ct` on((`ctip`.`customer_type_id` = `ct`.`id`))) ;

-- ----------------------------
-- View structure for v_dashboard_sales_amount
-- ----------------------------
DROP VIEW IF EXISTS `v_dashboard_sales_amount`;
CREATE VIEW `v_dashboard_sales_amount` AS select `sat`.`id` AS `sales_target_id`,`sat`.`shop_id` AS `shop_id`,`hdr`.`week_start_day_name` AS `week_start_day_name`,`hdr`.`start_year_date` AS `start_year_date`,`sat`.`current_day_target_amount` AS `current_day_target_amount`,`sat`.`last_calculated_date` AS `last_calculated_date`,`sat`.`weekly_amount` AS `weekly_amount`,`sat`.`monthly_amount` AS `monthly_amount`,`sat`.`yearly_amount` AS `yearly_amount`,`sat`.`lastyear_sameday_target_amount` AS `lastyear_sameday_target_amount`,`sat`.`lastyear_weekly_amount` AS `lastyear_weekly_amount`,`sat`.`lastyear_yearly_amount` AS `lastyear_yearly_amount` from (`target_settings_hdr` `hdr` left join `sales_amount_target` `sat` on(((`hdr`.`shop_id` = `sat`.`shop_id`) and (`hdr`.`year` = extract(year from curdate()))))) ;

-- ----------------------------
-- View structure for v_discounts
-- ----------------------------
DROP VIEW IF EXISTS `v_discounts`;
CREATE VIEW `v_discounts` AS select `discounts`.`id` AS `id`,`discounts`.`code` AS `code`,`discounts`.`name` AS `name`,`discounts`.`description` AS `description`,`discounts`.`is_percentage` AS `is_percentage`,`discounts`.`is_overridable` AS `is_overridable`,`discounts`.`is_item_specific` AS `is_item_specific`,`discounts`.`permitted_for` AS `permitted_for`,`discounts`.`is_promotion` AS `is_promotion`,`discounts`.`is_system` AS `is_system`,`discounts`.`price` AS `price`,`discounts`.`account_code` AS `account_code`,`discounts`.`grouping_quantity` AS `grouping_quantity`,`discounts`.`allow_editing` AS `allow_editing`,`discounts`.`is_valid` AS `is_valid`,`discounts`.`date_from` AS `date_from`,`discounts`.`date_to` AS `date_to`,`discounts`.`time_from` AS `time_from`,`discounts`.`time_to` AS `time_to`,`discounts`.`week_days` AS `week_days`,`discounts`.`created_by` AS `created_by`,`discounts`.`created_at` AS `created_at`,`discounts`.`updated_by` AS `updated_by`,`discounts`.`updated_at` AS `updated_at`,`discounts`.`publish_status` AS `publish_status`,`discounts`.`is_deleted` AS `is_deleted`,`discounts`.`is_synchable` AS `is_synchable`,`discounts`.`disc_password` AS `disc_password` from `discounts` where (((`discounts`.`date_from` IS NULL) or (cast(`discounts`.`date_from` as date) <= cast(now() as date))) and ((`discounts`.`date_to` IS NULL) or (cast(`discounts`.`date_to` as date) >= cast(now() as date))) and (`discounts`.`is_valid` = 1) and (`discounts`.`is_promotion` = 0) and (`discounts`.`is_deleted` = 0)) order by `discounts`.`name` ;

-- ----------------------------
-- View structure for v_duty_roaster
-- ----------------------------
DROP VIEW IF EXISTS `v_duty_roaster`;
CREATE VIEW `v_duty_roaster` AS select `txn_dr_details`.`dr_days_id` AS `dr_days_id`,`txn_dr_details`.`employee_id` AS `employee_id`,`txn_dr_details`.`shift_id` AS `shift_id`,`shop_shifts`.`name` AS `shift_name`,concat(convert(`txn_dr_days`.`year` using utf8),'-',convert(`txn_dr_days`.`month` using utf8),'-',convert(`txn_dr_days`.`day` using utf8),' ',`txn_dr_details`.`shift_in`) AS `shiftdate`,`txn_dr_details`.`shift_in` AS `shift_in`,`txn_dr_details`.`shift_out` AS `shift_out`,subtime(concat(convert(`txn_dr_days`.`year` using utf8),'-',convert(`txn_dr_days`.`month` using utf8),'-',convert(`txn_dr_days`.`day` using utf8),' ',`txn_dr_details`.`shift_in`),`shop_shifts`.`allowed_time_before_start`) AS `allowed_time_before_start`,addtime(concat(convert(`txn_dr_days`.`year` using utf8),'-',convert(`txn_dr_days`.`month` using utf8),'-',convert(`txn_dr_days`.`day` using utf8),' ',`txn_dr_details`.`shift_in`),`shop_shifts`.`allowed_time_after_start`) AS `allowed_time_after_start` from ((`txn_dr_days` join `txn_dr_details` on((`txn_dr_days`.`dr_days_id` = `txn_dr_details`.`dr_days_id`))) join `shop_shifts` on((`shop_shifts`.`id` = `txn_dr_details`.`shift_id`))) ;

-- ----------------------------
-- View structure for v_duty_roaster_details
-- ----------------------------
DROP VIEW IF EXISTS `v_duty_roaster_details`;
CREATE VIEW `v_duty_roaster_details` AS select `dtl`.`employee_id` AS `employee_id`,`dtl`.`shift_id` AS `shift_id`,`shop_shifts`.`name` AS `shift_name`,concat((`hdr`.`from_date` + interval (`dtl`.`day_no` - 1) day),' ',ifnull(`shop_shifts`.`allowed_time_before_start`,'00:00:00')) AS `allowed_time_before_start`,concat((`hdr`.`from_date` + interval (`dtl`.`day_no` - 1) day),' ',ifnull(`shop_shifts`.`allowed_time_after_start`,'00:00:00')) AS `allowed_time_after_start` from ((`dr_hdr` `hdr` join `dr_dtl` `dtl` on(((`hdr`.`id` = `dtl`.`dr_hdr_id`) and (`hdr`.`is_deleted` = 0)))) join `shop_shifts` on((`shop_shifts`.`id` = `dtl`.`shift_id`))) ;

-- ----------------------------
-- View structure for v_kitchens
-- ----------------------------
DROP VIEW IF EXISTS `v_kitchens`;
CREATE VIEW `v_kitchens` AS select `kitchens`.`id` AS `id`,`kitchens`.`code` AS `code`,`kitchens`.`name` AS `name`,`kitchens`.`description` AS `description` from `kitchens` where (`kitchens`.`is_deleted` = '0') ;


-- ----------------------------
-- View structure for v_login_sessions
-- ----------------------------
DROP VIEW IF EXISTS `v_login_sessions`;
CREATE
VIEW `v_login_sessions` AS
SELECT
	`login_sessions`.`cashier_shift_id` AS `cashier_shift_id`,
	`login_sessions`.`id` AS `login_sessions_id`,
	`login_sessions`.`pos_id` AS `pos_id`,
	`login_sessions`.`login_user_id` AS `login_user_id`,
	`login_sessions`.`start_at` AS `start_at`,
	`login_sessions`.`end_at` AS `end_at`,
	ifnull(
		`cashier_shifts`.`opening_till_id`,
		`cashier_shifts`.`auto_id`
	) AS `opening_till_id`,
	`users`.`name` AS `user_name`,
	`users`.`card_no` AS `card_no`,
	`stations`.`code` AS `station_code`,
	`stations`.`name` AS `station_name`,
	`stations`.`type` AS `station_type`
FROM
	(
		(
			(
				`login_sessions`
				JOIN `cashier_shifts` ON (
					(
						`login_sessions`.`cashier_shift_id` = `cashier_shifts`.`auto_id`
					)
				)
			)
			JOIN `users` ON (
				(
					`login_sessions`.`login_user_id` = `users`.`id`
				)
			)
		)
		JOIN `stations` ON (
			(
				`login_sessions`.`pos_id` = `stations`.`id`
			)
		)
	);

	-- ----------------------------
-- View structure for v_tab_login_sessions
-- ----------------------------
DROP VIEW IF EXISTS `v_tab_login_sessions`;
CREATE
VIEW `v_tab_login_sessions` AS
SELECT
	`login_sessions`.`id` AS `id`,
	`login_sessions`.`pos_id` AS `pos_id`,
	`login_sessions`.`cashier_shift_id` AS `cashier_shift_id`,
	`login_sessions`.`login_user_id` AS `login_user_id`,
	`login_sessions`.`start_at` AS `start_at`,
	`login_sessions`.`end_at` AS `end_at`,
	`stations`.`code` AS `station_code`,
	`stations`.`name` AS `station_name`
FROM
	(
		`stations`
		JOIN `login_sessions` ON (
			(
				`login_sessions`.`pos_id` = `stations`.`id`
			)
		)
	)
WHERE
	(`stations`.`type` = 3);
	
-- ----------------------------
-- View structure for v_employees
-- ----------------------------
DROP VIEW IF EXISTS `v_employees`;
CREATE VIEW `v_employees` AS select 
`emp`.`id` AS `id`,
`emp`.`code` AS `code`,
`emp`.`employee_category_id` AS `employee_category_id`,
`emp`.`department_id` AS `department_id`,
`emp`.`status` AS `status`,
`emp`.`sex` AS `sex`,
`emp`.`dob` AS `dob`,
`emp`.`f_name` AS `f_name`,
`emp`.`m_name` AS `m_name`,
`emp`.`l_name` AS `l_name`,
`emp`.`doj` AS `doj`,
`emp`.`wage_type` AS `wage_type`,
`emp`.`address` AS `address`,
`emp`.`country` AS `country`,
`emp`.`zip_code` AS `zip_code`,
`emp`.`phone` AS `phone`,
`emp`.`fax` AS `fax`,
`emp`.`email` AS `email`,
`emp`.`loc_address` AS `loc_address`,
`emp`.`loc_country` AS `loc_country`,
`emp`.`loc_zip_code` AS `loc_zip_code`,
`emp`.`loc_phone` AS `loc_phone`,
`emp`.`card_no` AS `card_no`,
`emp`.`loc_fax` AS `loc_fax`,
`emp`.`cost_per_hour` AS `cost_per_hour`,
`emp`.`work_permit` AS `work_permit`,
`emp`.`created_by` AS `created_by`,
`emp`.`created_at` AS `created_at`,
`emp`.`updated_by` AS `updated_by`,
`emp`.`updated_at` AS `updated_at`,
`emp`.`publish_status` AS `publish_status`,
`emp`.`is_deleted` AS `is_deleted`,
`emp`.`is_synchable` AS `is_synchable`,

`emp`.`id` AS `emp_id`,
`emp`.`code` AS `emp_code`,
concat(`emp`.`f_name`,' ',`emp`.`l_name`) AS `emp_name`,
ifnull(`emp`.`over_time_pay_rate`,0) AS `over_time_pay_rate`,
`ctg`.`code` AS `category_code`,
`ctg`.`name` AS `category_name` 
from (`employees` `emp` join `employee_categories` `ctg` on((`emp`.`employee_category_id` = `ctg`.`id`))) 
where ((`emp`.`is_deleted` = 0) and (`emp`.`status` = 1) and (`ctg`.`is_deleted` = 0)) ;


-- ----------------------------
-- View structure for v_employees
-- ----------------------------
DROP VIEW IF EXISTS `v_employees_attendance`;
CREATE  VIEW `v_employees_attendance` AS 
select `employees`.`id` AS `id`,`employees`.`code` AS `code`,
`employees`.`employee_category_id` AS `employee_category_id`,
`employees`.`department_id` AS `department_id`,`employees`.`status` AS `status`,
`employees`.`sex` AS `sex`,`employees`.`dob` AS `dob`,`employees`.`f_name` AS `f_name`,
`employees`.`m_name` AS `m_name`,`employees`.`l_name` AS `l_name`,`employees`.`doj` AS `doj`,
`employees`.`wage_type` AS `wage_type`,`employees`.`address` AS `address`,
`employees`.`country` AS `country`,`employees`.`zip_code` AS `zip_code`,
`employees`.`phone` AS `phone`,`employees`.`fax` AS `fax`,
`employees`.`email` AS `email`,`employees`.`loc_address` AS `loc_address`,
`employees`.`loc_country` AS `loc_country`,`employees`.`loc_zip_code` AS `loc_zip_code`,
`employees`.`loc_phone` AS `loc_phone`,`employees`.`card_no` AS `card_no`,
`employees`.`loc_fax` AS `loc_fax`,`employees`.`cost_per_hour` AS `cost_per_hour`,
`employees`.`over_time_pay_rate` AS `over_time_pay_rate`,`employees`.`work_permit` AS `work_permit`,
`employees`.`created_by` AS `created_by`,`employees`.`created_at` AS `created_at`,
`employees`.`updated_by` AS `updated_by`,`employees`.`updated_at` AS `updated_at`,
`employees`.`publish_status` AS `publish_status`,`employees`.`is_deleted` AS `is_deleted`,
`employees`.`is_system` AS `is_system`,`employees`.`is_synchable` AS `is_synchable`,
`employees`.`last_sync_at` AS `last_sync_at`,
ifnull((select 1 from `txn_staff_attendance` where ((`txn_staff_attendance`.`employee_id` = `employees`.`id`) and isnull(`txn_staff_attendance`.`shift_end_time`)) limit 1),0) AS `is_open` 
from `employees`
where ((`employees`.`is_deleted` = 0) and (`employees`.`status` = 1));
-- ----------------------------
-- View structure for v_flash_messages
-- ----------------------------
DROP VIEW IF EXISTS `v_flash_messages`;
CREATE VIEW `v_flash_messages` AS SELECT
*
FROM
	`flash_messages_hdr`
WHERE
	(
		(
			isnull(
				`flash_messages_hdr`.`from_date`
			)
			OR (
				cast(
					`flash_messages_hdr`.`from_date` AS date
				) <= cast(now() AS date)
			)
		)
		AND (
			isnull(
				`flash_messages_hdr`.`to_date`
			)
			OR (
				cast(
					`flash_messages_hdr`.`to_date` AS date
				) >= cast(now() AS date)
			)
		)
		AND (
			`flash_messages_hdr`.`display_status` <> 0
		)
		AND (
			`flash_messages_hdr`.`is_deleted` = 0
		)
	);

-- ----------------------------
-- View structure for v_hot_items
-- ----------------------------
DROP VIEW IF EXISTS `v_hot_items`;
CREATE VIEW `v_hot_items` AS SELECT
v_sale_items.*
from `v_sale_items`
where ((`v_sale_items`.`is_hot_item_1` = 1) or (`v_sale_items`.`is_hot_item_2` = 1) or (`v_sale_items`.`is_hot_item_3` = 1));

-- ----------------------------
-- View structure for v_item_classes
-- ----------------------------
DROP VIEW IF EXISTS `v_item_classes_non_empty`;
CREATE VIEW `v_item_classes_non_empty` AS SELECT
	`m`.`id` AS `id`,
	`m`.`code` AS `code`,
	`m`.`hsn_code` AS `hsn_code`,
	`m`.`name` AS `name`,
	`m`.`alternative_name` AS `alternative_name`,
	`m`.`department_id` AS `department_id`,
	`m`.`description` AS `description`,
	`m`.`menu_id` AS `menu_id`,
	`m`.`super_class_id` AS `super_class_id`,
	`m`.`tax_id` AS `tax_id`,
	`m`.`display_order` AS `display_order`,
	`m`.`account_code` AS `account_code`,
	`m`.`fg_color` AS `fg_color`,
	`m`.`bg_color` AS `bg_color`,
	`m`.`item_thumb` AS `item_thumb`,
	`m`.`created_by` AS `created_by`,
	`m`.`created_at` AS `created_at`,
	`m`.`updated_by` AS `updated_by`,
	`m`.`updated_at` AS `updated_at`,
	`m`.`publish_status` AS `publish_status`,
	`m`.`is_deleted` AS `is_deleted`,
	`m`.`is_synchable` AS `is_synchable`,
	(
		SELECT
			count(`si`.`id`)
		FROM
			`sale_items` `si`
		WHERE
			(
				(
					`si`.`sub_class_id` = `m`.`id`
				)
				AND (`si`.`is_deleted` = 0)
				AND (`si`.`is_valid` = 1)
			)
	) AS `item_count`,
	(
		SELECT
			`item_classes`.`code`
		FROM
			`item_classes`
		WHERE
			(
				`item_classes`.`id` = `m`.`super_class_id`
			)
	) AS `super_class_code`,
	ifnull(`m`.`print_order`, 9999) AS `print_order`
FROM
	`item_classes` `m`
WHERE
	(
		(
			(
				(
					SELECT
						1
					FROM
						`sale_items`
					WHERE
						(
							(
								`sale_items`.`is_deleted` = 0
							)
							AND (
								`sale_items`.`sub_class_id` = `m`.`id`
							)
						)
					LIMIT 1
				) > 0
			)
			OR (
				(
					SELECT
						1
					FROM
						`item_classes` `sic`
					WHERE
						(
							`sic`.`super_class_id` = `m`.`id`
						)
					LIMIT 1
				) > 0
			)
		)
		AND (`m`.`is_deleted` = 0)
	);

	-- ----------------------------
-- View structure for v_item_classes
-- ----------------------------
DROP VIEW IF EXISTS `v_item_classes`;
CREATE VIEW `v_item_classes` AS SELECT
	*
FROM
	`v_item_classes_non_empty`
WHERE
	(
		(
			`v_item_classes_non_empty`.`item_count` > 0
		)
		OR isnull(
			`v_item_classes_non_empty`.`super_class_id`
		)
	);

-- ----------------------------
-- View structure for v_menu_departments
-- ----------------------------
DROP VIEW IF EXISTS `v_menu_departments`;
CREATE VIEW `v_menu_departments` AS 
SELECT
menu_departments.id,
menu_departments.menu_id,
menu_departments.department_id,
menu_departments.publish_status,
menu_departments.is_deleted,
menu_departments.is_synchable,
menu_departments.last_sync_at
FROM
menu_departments
INNER JOIN shop_departments ON menu_departments.department_id = shop_departments.department_id
INNER JOIN departments ON shop_departments.department_id = departments.id
WHERE
menu_departments.is_deleted = 0 AND
shop_departments.is_deleted = 0 AND
departments.is_deleted = 0;

-- ----------------------------
-- View structure for v_menus
-- ----------------------------
DROP VIEW IF EXISTS `v_menus`;
CREATE VIEW `v_menus` AS 
SELECT *
FROM menus
WHERE
	(
		(`menus`.`is_active` = 1)
		AND (`menus`.`is_deleted` = 0)
		AND menus.id in (SELECT menu_id from v_menu_departments vmd WHERE vmd.is_deleted=0)
		
	);

-- ----------------------------
-- View structure for v_combo_contents
-- ----------------------------
DROP VIEW IF EXISTS `v_combo_contents`;
CREATE  VIEW `v_combo_contents` AS select `CC`.`id` AS `id`,`CC`.`code` AS `code`,`CC`.`name` AS `name`,`CC`.`description` AS `description`,`CC`.`uom_id` AS `uom_id`,`CC`.`created_by` AS `created_by`,`CC`.`created_at` AS `created_at`,`CC`.`updated_by` AS `updated_by`,`CC`.`updated_at` AS `updated_at`,`CC`.`publish_status` AS `publish_status`,`CC`.`is_deleted` AS `is_deleted`,`CC`.`is_synchable` AS `is_synchable`,`CC`.`last_sync_at` AS `last_sync_at` from `combo_contents` `CC` where (`CC`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_combo_content_substitutions
-- ----------------------------
DROP VIEW IF EXISTS `v_combo_content_substitutions`;
CREATE  VIEW `v_combo_content_substitutions` AS select `CCS`.`id` AS `id`,`CCS`.`combo_content_id` AS `combo_content_id`,`CCS`.`substitution_sale_item_id` AS `substitution_sale_item_id`,`CCS`.`price_diff` AS `price_diff`,`CCS`.`qty` AS `qty`,`CCS`.`is_default` AS `is_default`,`CCS`.`created_by` AS `created_by`,`CCS`.`created_at` AS `created_at`,`CCS`.`updated_by` AS `updated_by`,`CCS`.`updated_at` AS `updated_at`,`CCS`.`publish_status` AS `publish_status`,`CCS`.`is_deleted` AS `is_deleted`,`CCS`.`is_synchable` AS `is_synchable`,`CCS`.`last_sync_at` AS `last_sync_at`,`CC`.`code` AS `combo_content_code` from (`combo_content_substitutions` `CCS` join `v_combo_contents` `CC` on((`CCS`.`combo_content_id` = `CC`.`id`))) where (`CCS`.`is_deleted` = 0) ;


-- ----------------------------
-- View structure for v_sale_item_combo_contents
-- ----------------------------
DROP VIEW IF EXISTS `v_sale_item_combo_contents`;
CREATE  VIEW `v_sale_item_combo_contents` AS select `sale_item_combo_contents`.`id` AS `id`,`sale_item_combo_contents`.`combo_sale_item_id` AS `combo_sale_item_id`,`sale_item_combo_contents`.`combo_content_item_id` AS `combo_content_item_id`,`sale_item_combo_contents`.`max_items` AS `max_items`,`sale_item_combo_contents`.`created_by` AS `created_by`,`sale_item_combo_contents`.`created_at` AS `created_at`,`sale_item_combo_contents`.`updated_by` AS `updated_by`,`sale_item_combo_contents`.`updated_at` AS `updated_at`,`sale_item_combo_contents`.`publish_status` AS `publish_status`,`sale_item_combo_contents`.`is_deleted` AS `is_deleted`,`sale_item_combo_contents`.`is_synchable` AS `is_synchable`,`sale_item_combo_contents`.`last_sync_at` AS `last_sync_at`,`v_combo_contents`.`code` AS `code`,`v_combo_contents`.`name` AS `name`,`v_combo_contents`.`description` AS `description`,`v_combo_contents`.`uom_id` AS `uom_id`,`v_sale_items`.`code` AS `combo_sale_item_code` from ((`sale_item_combo_contents` join `v_combo_contents` on((`sale_item_combo_contents`.`combo_content_item_id` = `v_combo_contents`.`id`))) join `v_sale_items` on((`sale_item_combo_contents`.`combo_sale_item_id` = `v_sale_items`.`id`))) where (`sale_item_combo_contents`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_sale_items_combo_substitutions
-- ----------------------------
DROP VIEW IF EXISTS `v_sale_item_combo_substitutions`;
CREATE 
VIEW `v_sale_item_combo_substitutions`AS 
SELECT
sale_item_combo_substitutions.id,
sale_item_combo_contents.combo_sale_item_id AS sale_item_id,
sale_item_combo_contents.id AS combo_content_id,
sale_item_combo_substitutions.substitution_sale_item_id,
sale_item_combo_substitutions.quantity,
sale_item_combo_substitutions.price_difference,
sale_item_combo_substitutions.is_default
FROM
sale_item_combo_contents
INNER JOIN sale_item_combo_substitutions ON sale_item_combo_substitutions.sale_item_combo_content_id = sale_item_combo_contents.id
WHERE 
sale_item_combo_substitutions.is_deleted=0;

-- ----------------------------
-- View structure for v_promotions
-- ----------------------------
DROP VIEW IF EXISTS `v_promotions`;
CREATE VIEW `v_promotions` AS select `discounts`.`id` AS `id`,`discounts`.`code` AS `code`,`discounts`.`name` AS `name`,`discounts`.`description` AS `description`,`discounts`.`is_percentage` AS `is_percentage`,`discounts`.`is_overridable` AS `is_overridable`,`discounts`.`is_item_specific` AS `is_item_specific`,`discounts`.`permitted_for` AS `permitted_for`,`discounts`.`is_promotion` AS `is_promotion`,`discounts`.`is_system` AS `is_system`,`discounts`.`price` AS `price`,`discounts`.`account_code` AS `account_code`,`discounts`.`grouping_quantity` AS `grouping_quantity`,`discounts`.`allow_editing` AS `allow_editing`,`discounts`.`is_valid` AS `is_valid`,`discounts`.`date_from` AS `date_from`,`discounts`.`date_to` AS `date_to`,`discounts`.`time_from` AS `time_from`,`discounts`.`time_to` AS `time_to`,`discounts`.`week_days` AS `week_days`,`discounts`.`created_by` AS `created_by`,`discounts`.`created_at` AS `created_at`,`discounts`.`updated_by` AS `updated_by`,`discounts`.`updated_at` AS `updated_at`,`discounts`.`publish_status` AS `publish_status`,`discounts`.`is_deleted` AS `is_deleted`,`discounts`.`is_synchable` AS `is_synchable`,`discounts`.`disc_password` AS `disc_password` from `discounts` where (((`discounts`.`date_from` IS NULL) or (cast(`discounts`.`date_from` as date) <= cast(now() as date))) and ((`discounts`.`date_to` IS NULL) or (cast(`discounts`.`date_to` as date) >= cast(now() as date))) and (`discounts`.`is_valid` = 1) and (`discounts`.`is_promotion` = 1) and (`discounts`.`is_deleted` = 0)) ;

-- ----------------------------
-- View structure for v_sale_item_choices
-- ----------------------------
DROP VIEW IF EXISTS `v_sale_item_choices`;
CREATE VIEW `v_sale_item_choices` AS select `sic`.`id` AS `id`,`sic`.`sale_item_id` AS `sale_item_id`,`sic`.`choice_id` AS `choice_id`,`sic`.`free_items` AS `free_items`,`sic`.`max_items` AS `max_items`,`sic`.`created_by` AS `created_by`,`sic`.`created_at` AS `created_at`,`sic`.`updated_by` AS `updated_by`,`sic`.`updated_at` AS `updated_at`,`sic`.`publish_status` AS `publish_status`,`sic`.`is_deleted` AS `is_deleted`,`sic`.`is_synchable` AS `is_synchable`,`si`.`code` AS `sale_item_code` from (`sale_item_choices` `sic` join `v_sale_items` `si` on((`sic`.`sale_item_id` = `si`.`id`))) where (`sic`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_sale_item_discounts
-- ----------------------------
DROP VIEW IF EXISTS `v_sale_item_discounts`;
CREATE VIEW `v_sale_item_discounts` AS select `vd`.`id` AS `id`,`vd`.`code` AS `code`,`vd`.`name` AS `name`,`vd`.`description` AS `description`,`vd`.`is_percentage` AS `is_percentage`,`vd`.`is_overridable` AS `is_overridable`,`vd`.`is_item_specific` AS `is_item_specific`,`vd`.`permitted_for` AS `permitted_for`,`vd`.`is_promotion` AS `is_promotion`,`vd`.`is_system` AS `is_system`,`sid`.`price` AS `price`,`vd`.`grouping_quantity` AS `grouping_quantity`,`vd`.`allow_editing` AS `allow_editing`,`vd`.`is_valid` AS `is_valid`,`vd`.`date_from` AS `date_from`,`vd`.`date_to` AS `date_to`,`vd`.`time_from` AS `time_from`,`vd`.`time_to` AS `time_to`,`vd`.`week_days` AS `week_days`,`vd`.`created_by` AS `created_by`,`vd`.`created_at` AS `created_at`,`vd`.`updated_by` AS `updated_by`,`vd`.`updated_at` AS `updated_at`,`vd`.`publish_status` AS `publish_status`,`vd`.`is_deleted` AS `is_deleted`,`vd`.`is_synchable` AS `is_synchable`,`vd`.`disc_password` AS `disc_password`,`sid`.`sale_item_id` AS `sale_item_id` from (`v_discounts` `vd` join `sale_item_discounts` `sid` on((`sid`.`discount_id` = `vd`.`id`))) where (`sid`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_sale_item_promotions
-- ----------------------------
DROP VIEW IF EXISTS `v_sale_item_promotions`;
CREATE VIEW `v_sale_item_promotions` AS select `sid`.`price` AS `price`,`vp`.`id` AS `id`,`vp`.`code` AS `code`,`vp`.`name` AS `name`,`vp`.`description` AS `description`,`vp`.`is_percentage` AS `is_percentage`,`vp`.`is_overridable` AS `is_overridable`,`vp`.`is_item_specific` AS `is_item_specific`,`vp`.`permitted_for` AS `permitted_for`,`vp`.`is_promotion` AS `is_promotion`,`vp`.`is_system` AS `is_system`,`vp`.`account_code` AS `account_code`,`vp`.`grouping_quantity` AS `grouping_quantity`,`vp`.`allow_editing` AS `allow_editing`,`vp`.`is_valid` AS `is_valid`,`vp`.`date_from` AS `date_from`,`vp`.`date_to` AS `date_to`,`vp`.`time_from` AS `time_from`,`vp`.`time_to` AS `time_to`,`vp`.`week_days` AS `week_days`,`vp`.`created_by` AS `created_by`,`vp`.`created_at` AS `created_at`,`vp`.`updated_by` AS `updated_by`,`vp`.`updated_at` AS `updated_at`,`vp`.`publish_status` AS `publish_status`,`vp`.`is_deleted` AS `is_deleted`,`vp`.`is_synchable` AS `is_synchable`,`vp`.`disc_password` AS `disc_password`,`sid`.`sale_item_id` AS `sale_item_id` from (`v_promotions` `vp` join `sale_item_discounts` `sid` on((`sid`.`discount_id` = `vp`.`id`))) ;

-- ----------------------------
-- View structure for v_serving_table_locations
-- ----------------------------
DROP VIEW IF EXISTS `v_serving_table_locations`;
CREATE VIEW `v_serving_table_locations` AS 
SELECT
*
from `serving_table_location`
where (`serving_table_location`.`is_deleted` = 0);

-- ----------------------------
-- View structure for v_serving_tables
-- ----------------------------
DROP VIEW IF EXISTS `v_serving_tables`;
CREATE VIEW `v_serving_tables` AS  
SELECT
	`serving_tables`.`id` AS `id`,
	`serving_tables`.`code` AS `code`,
	`serving_tables`.`name` AS `name`,
	`serving_tables`.`description` AS `description`,
	`serving_tables`.`is_system` AS `is_system`,
	`serving_tables`.`is_valid` AS `is_valid`,
	`serving_tables`.`covers` AS `covers`,
	`serving_tables`.`row_position` AS `row_position`,
	`serving_tables`.`column_position` AS `column_position`,
	`serving_tables`.`serving_table_location_id` AS `serving_table_location_id`,
	`serving_tables`.`status` AS `status`,
	`serving_tables`.`created_by` AS `created_by`,
	`serving_tables`.`created_at` AS `created_at`,
	`serving_tables`.`updated_by` AS `updated_by`,
	`serving_tables`.`updated_at` AS `updated_at`,
	`serving_tables`.`publish_status` AS `publish_status`,
	`serving_tables`.`is_deleted` AS `is_deleted`,
	`serving_tables`.`is_synchable` AS `is_synchable`,
	`serving_table_images`.`image` AS `layout_image`,
	`serving_table_images`.`height` AS `layout_height`,
	`serving_table_images`.`width` AS `layout_width`,
	`serving_table_location`.`name` AS `serving_table_location_name`,
	`serving_table_location`.`code` AS `serving_table_location_code`
FROM
	(
		(
			`serving_tables`
			LEFT JOIN `serving_table_images` ON (
				(
					`serving_tables`.`layout_image` = `serving_table_images`.`id`
				)
			)
		)
		JOIN `serving_table_location` ON (
			(
				`serving_tables`.`serving_table_location_id` = `serving_table_location`.`id`
			)
		)
	)
WHERE
	(
		(
			`serving_tables`.`is_valid` = 1
		)
		AND (
			`serving_tables`.`is_deleted` = 0
		)
	)
ORDER BY
	`serving_tables`.`name`;
	
-- ----------------------------
-- View structure for v_cashier_shift
-- ----------------------------
DROP VIEW IF EXISTS `v_cashier_shift`;
CREATE  VIEW `v_cashier_shift` AS SELECT
	`shop_shifts`.`name` AS `shift_name`,
	`cashier_shifts`.`auto_id` AS `auto_id`,
	`cashier_shifts`.`cashier_id` AS `cashier_id`,
	`cashier_shifts`.`pos_id` AS `pos_id`,
	`cashier_shifts`.`opening_date` AS `opening_date`,
	`cashier_shifts`.`opening_time` AS `opening_time`,
	`cashier_shifts`.`shift_id` AS `shift_id`,
	`cashier_shifts`.`opening_till_id` AS `opening_till_id`,
	`cashier_shifts`.`opening_float` AS `opening_float`,
	`cashier_shifts`.`closing_date` AS `closing_date`,
	`cashier_shifts`.`closing_time` AS `closing_time`,
	`cashier_shifts`.`collection_cash` AS `collection_cash`,
	`cashier_shifts`.`collection_card` AS `collection_card`,
	`cashier_shifts`.`collection_voucher` AS `collection_voucher`,
	`cashier_shifts`.`collection_company` AS `collection_company`,
	`cashier_shifts`.`daily_cashout` AS `daily_cashout`,
	`cashier_shifts`.`closing_float` AS `closing_float`,
	`cashier_shifts`.`balance_cash` AS `balance_cash`,
	`cashier_shifts`.`balance_voucher` AS `balance_voucher`,
	`cashier_shifts`.`balance_voucher_returned` AS `balance_voucher_returned`,
	`cashier_shifts`.`cash_out` AS `cash_out`,
	`cashier_shifts`.`cash_refund` AS `cash_refund`,
	`cashier_shifts`.`card_refund` AS `card_refund`,
	`cashier_shifts`.`voucher_refund` AS `voucher_refund`,
	`cashier_shifts`.`company_refund` AS `company_refund`,
	`cashier_shifts`.`total_refund` AS `total_refund`,
	`cashier_shifts`.`sync_status` AS `sync_status`,
	`cashier_shifts`.`sync_message` AS `sync_message`,
	`cashier_shifts`.`is_open_till` AS `is_open_till`,
	`cashier_shifts`.`last_sync_at` AS `last_sync_at`
FROM
	(
		`cashier_shifts`
		JOIN `shop_shifts` ON (
			(
				`cashier_shifts`.`shift_id` = `shop_shifts`.`id`
			)
		)
	);
	

-- ----------------------------
-- View structure for v_shop_target_amount
-- ----------------------------
DROP VIEW IF EXISTS `v_shop_target_amount`;
CREATE VIEW `v_shop_target_amount` AS select `hdr`.`shop_id` AS `shop_id`,`hdr`.`year` AS `year`,`dtl`.`week_no` AS `week_no`,`dtl`.`target_amount` AS `target_amount`,`dtl`.`labour_hour` AS `labour_hour`,`dtl`.`labour_cost` AS `labour_cost` from (`target_settings_hdr` `hdr` join `target_settings_dtl` `dtl` on((`hdr`.`id` = `dtl`.`target_settings_hdr_id`))) order by `hdr`.`shop_id`,`hdr`.`year`,`dtl`.`week_no` ;

-- ----------------------------
-- View structure for v_staff_dutyroaster_details
-- ----------------------------
DROP VIEW IF EXISTS `v_staff_dutyroaster_details`;
CREATE VIEW `v_staff_dutyroaster_details` AS select `dtl`.`dr_hdr_id` AS `dr_hdr_id`,`hdr`.`from_date` AS `from_date`,`hdr`.`to_date` AS `to_date`,`hdr`.`is_active` AS `is_active`,`dtl`.`shift_id` AS `shift_id`,`dtl`.`employee_id` AS `employee_id`,`dtl`.`day_no` AS `day_no`,`dtl`.`start_time` AS `start_time`,`dtl`.`end_time` AS `end_time`,(`hdr`.`from_date` + interval `dtl`.`day_no` day) AS `c_date`,subtime(concat((`hdr`.`from_date` + interval `dtl`.`day_no` day),' ',`sh`.`start_time`),`sh`.`allowed_time_before_start`) AS `start_date_from`,addtime(concat((`hdr`.`from_date` + interval `dtl`.`day_no` day),' ',`sh`.`start_time`),`sh`.`allowed_time_after_start`) AS `start_date_to`,subtime(concat((`hdr`.`from_date` + interval `dtl`.`day_no` day),' ',`sh`.`end_time`),`sh`.`allowed_time_before_end`) AS `end_date_from`,addtime(concat((`hdr`.`from_date` + interval `dtl`.`day_no` day),' ',`sh`.`end_time`),`sh`.`allowed_time_after_end`) AS `end_date_to` from ((`dr_hdr` `hdr` join `dr_dtl` `dtl` on(((`hdr`.`id` = `dtl`.`dr_hdr_id`) and (`hdr`.`is_active` = 1) and (`hdr`.`is_deleted` = 0)))) join `shop_shifts` `sh` on(((`sh`.`id` = `dtl`.`shift_id`) and (`sh`.`is_deleted` = 0)))) ;

-- ----------------------------
-- View structure for v_stations
-- ----------------------------
DROP VIEW IF EXISTS `v_stations`;
CREATE VIEW `v_stations` AS select `stations`.`id` AS `id`,`stations`.`code` AS `code`,`stations`.`name` AS `name`,`stations`.`description` AS `description`,`stations`.`type` AS `type`,`stations`.`created_by` AS `created_by`,`stations`.`created_at` AS `created_at`,`stations`.`updated_by` AS `updated_by`,`stations`.`updated_at` AS `updated_at`,`stations`.`publish_status` AS `publish_status`,`stations`.`is_deleted` AS `is_deleted`,`stations`.`is_synchable` AS `is_synchable` from `stations` where (`stations`.`is_deleted` = 0) ;

-- ----------------------------
-- View structure for v_tab_stations
-- ----------------------------
DROP VIEW IF EXISTS `v_tab_stations`;
CREATE VIEW `v_tab_stations` AS 
SELECT
	`stations`.`id` AS `id`,
	`stations`.`code` AS `code`,
	`stations`.`name` AS `name`,
	`stations`.`description` AS `description`,
	`stations`.`type` AS `type`,
	`stations`.`created_by` AS `created_by`,
	`stations`.`created_at` AS `created_at`,
	`stations`.`updated_by` AS `updated_by`,
	`stations`.`updated_at` AS `updated_at`,
	`stations`.`publish_status` AS `publish_status`,
	`stations`.`is_deleted` AS `is_deleted`,
	`stations`.`is_synchable` AS `is_synchable`,
	`stations`.`last_sync_at` AS `last_sync_at`
FROM
	`stations`
WHERE
	(
		(`stations`.`type` = 3)
		AND (`stations`.`is_deleted` = 0)
	);

-- ----------------------------
-- View structure for v_sync_settings_server_to_terminal
-- ----------------------------
DROP VIEW IF EXISTS `v_sync_settings_server_to_terminal`;
CREATE VIEW `v_sync_settings_server_to_terminal` AS
SELECT
terminal_sync_table_settings.id,
terminal_sync_table_settings.sync_order,
terminal_sync_table_settings.table_id,
terminal_sync_table_settings.table_name,
terminal_sync_table_settings.parent_table_id,
terminal_sync_table_settings.table_criteria,
terminal_sync_table_settings.order_by,
terminal_sync_table_settings.column_to_exclude,
terminal_sync_table_settings.pkey,
terminal_sync_table_settings.web_param_value,
terminal_sync_table_settings.is_sync_server_to_terminal,
terminal_sync_table_settings.is_sync_terminal_to_server,
terminal_sync_table_settings.is_sync_terminal_to_tab,
terminal_sync_table_settings.is_sync_tab_to_terminal,
terminal_sync_table_settings.remarks,
terminal_sync_table_settings.last_sync_at
FROM `terminal_sync_table_settings`
WHERE (`terminal_sync_table_settings`.`is_sync_server_to_terminal` = 1) ;


-- ----------------------------
-- View structure for v_sync_settings_tab_to_terminal
-- ----------------------------
DROP VIEW IF EXISTS `v_sync_settings_tab_to_terminal`;
CREATE VIEW `v_sync_settings_tab_to_terminal` AS
SELECT
terminal_sync_table_settings.id,
terminal_sync_table_settings.sync_order,
terminal_sync_table_settings.table_id,
terminal_sync_table_settings.table_name,
terminal_sync_table_settings.parent_table_id,
terminal_sync_table_settings.table_criteria,
terminal_sync_table_settings.order_by,
terminal_sync_table_settings.column_to_exclude,
terminal_sync_table_settings.pkey,
terminal_sync_table_settings.web_param_value,
terminal_sync_table_settings.is_sync_server_to_terminal,
terminal_sync_table_settings.is_sync_terminal_to_server,
terminal_sync_table_settings.is_sync_terminal_to_tab,
terminal_sync_table_settings.is_sync_tab_to_terminal,
terminal_sync_table_settings.remarks,
terminal_sync_table_settings.last_sync_at
FROM `terminal_sync_table_settings`
WHERE (`terminal_sync_table_settings`.`is_sync_tab_to_terminal` = 1) ;


-- ----------------------------
-- View structure for v_sync_settings_terminal_to_server
-- ----------------------------
DROP VIEW IF EXISTS `v_sync_settings_terminal_to_server`;
CREATE VIEW `v_sync_settings_terminal_to_server` AS
SELECT
terminal_sync_table_settings.id,
terminal_sync_table_settings.sync_order,
terminal_sync_table_settings.table_id,
terminal_sync_table_settings.table_name,
terminal_sync_table_settings.parent_table_id,
terminal_sync_table_settings.table_criteria,
terminal_sync_table_settings.order_by,
terminal_sync_table_settings.column_to_exclude,
terminal_sync_table_settings.pkey,
terminal_sync_table_settings.web_param_value,
terminal_sync_table_settings.is_sync_server_to_terminal,
terminal_sync_table_settings.is_sync_terminal_to_server,
terminal_sync_table_settings.is_sync_terminal_to_tab,
terminal_sync_table_settings.is_sync_tab_to_terminal,
terminal_sync_table_settings.remarks,
terminal_sync_table_settings.last_sync_at
FROM `terminal_sync_table_settings`
WHERE (`terminal_sync_table_settings`.`is_sync_terminal_to_server` = 1) ;

-- ----------------------------
-- View structure for v_sync_settings_terminal_to_tab
-- ----------------------------
DROP VIEW IF EXISTS `v_sync_settings_terminal_to_tab`;
CREATE VIEW `v_sync_settings_terminal_to_tab` AS
SELECT
terminal_sync_table_settings.id,
terminal_sync_table_settings.sync_order,
terminal_sync_table_settings.table_id,
terminal_sync_table_settings.table_name,
terminal_sync_table_settings.parent_table_id,
terminal_sync_table_settings.table_criteria,
terminal_sync_table_settings.order_by,
terminal_sync_table_settings.column_to_exclude,
terminal_sync_table_settings.pkey,
terminal_sync_table_settings.web_param_value,
terminal_sync_table_settings.is_sync_server_to_terminal,
terminal_sync_table_settings.is_sync_terminal_to_server,
terminal_sync_table_settings.is_sync_terminal_to_tab,
terminal_sync_table_settings.is_sync_tab_to_terminal,
terminal_sync_table_settings.remarks,
terminal_sync_table_settings.last_sync_at
FROM `terminal_sync_table_settings`
WHERE (`terminal_sync_table_settings`.`is_sync_terminal_to_tab` = 1) ; 

-- ----------------------------
-- View structure for v_voucher_types
-- ----------------------------
DROP VIEW IF EXISTS `v_voucher_types`;
CREATE VIEW `v_voucher_types` AS select `voucher_types`.`id` AS `id`,`voucher_types`.`code` AS `code`,`voucher_types`.`name` AS `name`,`voucher_types`.`description` AS `description`,`voucher_types`.`voucher_type` AS `voucher_type`,`voucher_types`.`value` AS `value`,`voucher_types`.`is_overridable` AS `is_overridable`,`voucher_types`.`is_change_payable` AS `is_change_payable`,`voucher_types`.`account_code` AS `account_code`,`voucher_types`.`is_valid` AS `is_valid`,`voucher_types`.`created_by` AS `created_by`,`voucher_types`.`created_at` AS `created_at`,`voucher_types`.`updated_by` AS `updated_by`,`voucher_types`.`updated_at` AS `updated_at`,`voucher_types`.`publish_status` AS `publish_status`,`voucher_types`.`is_deleted` AS `is_deleted`,`voucher_types`.`is_synchable` AS `is_synchable` from `voucher_types` where ((`voucher_types`.`is_valid` = 1) and (`voucher_types`.`is_deleted` = 0)) ;

-- ----------------------------
-- View structure for v_waiters
-- ----------------------------
DROP VIEW IF EXISTS `v_waiters`;
CREATE VIEW `v_waiters` AS select `v_employees`.`id` AS `id`,`v_employees`.`code` AS `code`,`v_employees`.`employee_category_id` AS `employee_category_id`,`v_employees`.`department_id` AS `department_id`,`v_employees`.`status` AS `status`,`v_employees`.`sex` AS `sex`,`v_employees`.`dob` AS `dob`,`v_employees`.`f_name` AS `f_name`,`v_employees`.`m_name` AS `m_name`,`v_employees`.`l_name` AS `l_name`,`v_employees`.`doj` AS `doj`,`v_employees`.`wage_type` AS `wage_type`,`v_employees`.`address` AS `address`,`v_employees`.`country` AS `country`,`v_employees`.`zip_code` AS `zip_code`,`v_employees`.`phone` AS `phone`,`v_employees`.`fax` AS `fax`,`v_employees`.`email` AS `email`,`v_employees`.`loc_address` AS `loc_address`,`v_employees`.`loc_country` AS `loc_country`,`v_employees`.`loc_zip_code` AS `loc_zip_code`,`v_employees`.`loc_phone` AS `loc_phone`,`v_employees`.`card_no` AS `card_no`,`v_employees`.`loc_fax` AS `loc_fax`,`v_employees`.`cost_per_hour` AS `cost_per_hour`,`v_employees`.`work_permit` AS `work_permit`,`v_employees`.`created_by` AS `created_by`,`v_employees`.`created_at` AS `created_at`,`v_employees`.`updated_by` AS `updated_by`,`v_employees`.`updated_at` AS `updated_at`,`v_employees`.`publish_status` AS `publish_status`,`v_employees`.`is_deleted` AS `is_deleted`,`v_employees`.`is_synchable` AS `is_synchable`,`v_employees`.`emp_id` AS `emp_id`,`v_employees`.`emp_code` AS `emp_code`,`v_employees`.`over_time_pay_rate` AS `over_time_pay_rate`,`v_employees`.`category_code` AS `category_code`,`v_employees`.`category_name` AS `category_name` from `v_employees` where (`v_employees`.`employee_category_id` = (select `employee_categories`.`id` from `employee_categories` where (`employee_categories`.`code` = 'WAITER'))) ;

-- ----------------------------
-- View structure for v_stock_item_mst
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_item_mst`;
CREATE VIEW `v_stock_item_mst` AS SELECT
	`stock_items`.`id` AS `id`,
	`stock_items`.`code` AS `code`,
	`stock_items`.`name` AS `name`,
	`stock_items`.`description` AS `description`,
	`stock_items`.`supplier_product_code` AS `supplier_product_code`,
	`stock_items`.`stock_item_attributes` AS `stock_item_attributes`,
	`stock_items`.`stock_item_location` AS `stock_item_location`,
	`stock_items`.`stock_item_category_id` AS `stock_item_category_id`,
	`stock_items`.`part_no` AS `part_no`,
	`stock_items`.`market_valuation_method` AS `market_valuation_method`,
	`stock_items`.`movement_method` AS `movement_method`,
	`uoms`.`uom_symbol` AS `uom_symbol`,
	`stock_items`.`qty_on_hand` AS `qty_on_hand`,
	`stock_items`.`unit_price` AS `unit_price`,
	`stock_items`.`optimum_level` AS `optimum_level`,
	`stock_items`.`reorder_level` AS `reorder_level`,
	`stock_items`.`reorder_qty` AS `reorder_qty`,
	`stock_items`.`preferred_supplier_id` AS `preferred_supplier_id`,
	`stock_items`.`last_purchase_unit_price` AS `last_purchase_unit_price`,
	`stock_items`.`last_purchase_supplier_id` AS `last_purchase_supplier_id`,
	`stock_items`.`tax_id` AS `tax_id`,
	`stock_items`.`tax_calculation_method` AS `tax_calculation_method`,
	`stock_items`.`is_service_item` AS `is_service_item`,
	`stock_items`.`is_valid` AS `is_valid`,
	`stock_items`.`is_manufactured` AS `is_manufactured`,
	`stock_items`.`is_sellable` AS `is_sellable`,
	`stock_items`.`qty_manufactured` AS `qty_manufactured`,
	`stock_items`.`top_consumption_rank` AS `top_consumption_rank`,
	`stock_items`.`created_by` AS `created_by`,
	`stock_items`.`created_at` AS `created_at`,
	`stock_items`.`updated_by` AS `updated_by`,
	`stock_items`.`updated_at` AS `updated_at`,
	`stock_items`.`publish_status` AS `publish_status`,
	`stock_items`.`is_system` AS `is_system`,
	`stock_items`.`is_deleted` AS `is_deleted`,
	`stock_items`.`is_synchable` AS `is_synchable`
FROM
	(
		`stock_items`
		LEFT JOIN `uoms` ON (
			(
				`uoms`.`id` = `stock_items`.`uom_id`
			)
		)
	)
WHERE
	(
		(
			`stock_items`.`is_deleted` = 0
		)
		AND (
			`stock_items`.`is_system` = 0
		)
	);
	
-- ----------------------------
-- View structure for v_stock_item
-- For Rreports
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_item`;
CREATE VIEW `v_stock_item` AS 
SELECT
	`stock_items`.`id` AS `id`,
	`stock_items`.`code` AS `code`,
	`stock_items`.`name` AS `name`,
	`stock_items`.`description` AS `description`,
	`stock_items`.`supplier_product_code` AS `supplier_product_code`,
	`stock_items`.`stock_item_attributes` AS `stock_item_attributes`,
	`stock_items`.`stock_item_location` AS `stock_item_location`,
	`stock_items`.`stock_item_category_id` AS `stock_item_category_id`,
	`stock_items`.`part_no` AS `part_no`,
	`stock_items`.`market_valuation_method` AS `market_valuation_method`,
	`stock_items`.`movement_method` AS `movement_method`,
	sum(
		`v_stock_in_out_preview`.`qty_received`
	) AS `qty_on_hand`,
	`stock_items`.`uom_id` AS `uom_id`,
	`stock_items`.`unit_price` AS `unit_price`,
	`stock_items`.`optimum_level` AS `optimum_level`,
	`stock_items`.`reorder_level` AS `reorder_level`,
	`stock_items`.`reorder_qty` AS `reorder_qty`,
	`stock_items`.`preferred_supplier_id` AS `preferred_supplier_id`,
	`stock_items`.`last_purchase_unit_price` AS `last_purchase_unit_price`,
	`stock_items`.`last_purchase_supplier_id` AS `last_purchase_supplier_id`,
	`stock_items`.`tax_id` AS `tax_id`,
	`stock_items`.`tax_calculation_method` AS `tax_calculation_method`,
	`stock_items`.`is_service_item` AS `is_service_item`,
	`stock_items`.`is_valid` AS `is_valid`,
	`stock_items`.`is_manufactured` AS `is_manufactured`,
	`stock_items`.`is_sellable` AS `is_sellable`,
	`stock_items`.`qty_manufactured` AS `qty_manufactured`,
	`stock_items`.`top_consumption_rank` AS `top_consumption_rank`,
	`stock_items`.`created_by` AS `created_by`,
	`stock_items`.`created_at` AS `created_at`,
	`stock_items`.`updated_by` AS `updated_by`,
	`stock_items`.`updated_at` AS `updated_at`,
	`stock_items`.`publish_status` AS `publish_status`,
	`stock_items`.`is_deleted` AS `is_deleted`,
	`stock_items`.`is_synchable` AS `is_synchable`
FROM
	(
		`stock_items`
		LEFT JOIN `v_stock_in_out_preview` ON (
			(
				`stock_items`.`id` = `v_stock_in_out_preview`.`stock_item_id`
			)
		)
	)
WHERE
	(
		`stock_items`.`is_deleted` = 0
	)
GROUP BY
	`stock_items`.`id`;


-- ----------------------------
-- View structure for v_order_dtl_choices
-- ----------------------------
DROP VIEW IF EXISTS `v_order_dtl_choices`;
CREATE VIEW `v_order_dtl_choices` AS 
select `order_dtls`.`id` AS `order_dtl_id`,`order_dtls`.`sale_item_choices_choice_id` AS `id`,`order_dtls`.`sale_item_choices_choice_code` AS `code`,`order_dtls`.`sale_item_choices_choice_name` AS `name`,`order_dtls`.`sale_item_choices_choice_description` AS `description`,`order_dtls`.`sale_item_choices_choice_is_global` AS `is_global` from `order_dtls` where (`order_dtls`.`item_type` = 1) ;

-- ----------------------------
-- View structure for v_order_dtl_sale_item_choices
-- ----------------------------
DROP VIEW IF EXISTS `v_order_dtl_sale_item_choices`;
CREATE VIEW `v_order_dtl_sale_item_choices` AS select `order_dtls`.`id` AS `order_dtl_id`,`order_dtls`.`sale_item_choices_id` AS `id`,`order_dtls`.`sale_item_id` AS `sale_item_id`,`order_dtls`.`sale_item_code` AS `sale_item_code`,`order_dtls`.`sale_item_choices_free_items` AS `free_items`,`order_dtls`.`sale_item_choices_max_items` AS `max_items` from `order_dtls` where (`order_dtls`.`item_type` = 1) ;

DROP VIEW IF EXISTS `v_order_dtl_tax`;
CREATE  VIEW `v_order_dtl_tax` AS SELECT
	`dtls`.`id` AS `id`,
	`dtls`.`order_id` AS `order_id`,
	`dtls`.`sale_item_id` AS `sale_item_id`,
	`dtls`.`sale_item_code` AS `sale_item_code`,
	`dtls`.`sub_class_id` AS `sub_class_id`,
	`dtls`.`sub_class_code` AS `sub_class_code`,
	`dtls`.`sub_class_name` AS `sub_class_name`,
	`dtls`.`name` AS `name`,
	`dtls`.`description` AS `description`,
	`dtls`.`alternative_name` AS `alternative_name`,
	`dtls`.`name_to_print` AS `name_to_print`,
	`dtls`.`alternative_name_to_print` AS `alternative_name_to_print`,
	`dtls`.`qty` AS `qty`,
	`dtls`.`uom_code` AS `uom_code`,
	`dtls`.`uom_name` AS `uom_name`,
	`dtls`.`uom_symbol` AS `uom_symbol`,
	`dtls`.`tax_calculation_method` AS `tax_calculation_method`,
	`dtls`.`is_open` AS `is_open`,
	`dtls`.`is_combo_item` AS `is_combo_item`,
	`dtls`.`fixed_price` AS `fixed_price`,
	`dtls`.`customer_price_variance` AS `customer_price_variance`,
	`dtls`.`tax_id` AS `tax_id`,
	`dtls`.`tax_id_home_service` AS `tax_id_home_service`,
	`dtls`.`tax_id_table_service` AS `tax_id_table_service`,
	`dtls`.`tax_id_take_away_service` AS `tax_id_take_away_service`,
	`dtls`.`tax_code` AS `tax_code`,
	`dtls`.`tax_name` AS `tax_name`,
	`dtls`.`is_tax1_applied` AS `is_tax1_applied`,
	`dtls`.`tax1_name` AS `tax1_name`,
	`dtls`.`tax1_pc` AS `tax1_pc`,
	`dtls`.`tax1_amount` AS `tax1_amount`,
	`dtls`.`is_tax2_applied` AS `is_tax2_applied`,
	`dtls`.`tax2_name` AS `tax2_name`,
	`dtls`.`tax2_pc` AS `tax2_pc`,
	`dtls`.`tax2_amount` AS `tax2_amount`,
	`dtls`.`is_tax3_applied` AS `is_tax3_applied`,
	`dtls`.`tax3_name` AS `tax3_name`,
	`dtls`.`tax3_pc` AS `tax3_pc`,
	`dtls`.`tax3_amount` AS `tax3_amount`,
	`dtls`.`is_gst_applied` AS `is_gst_applied`,
	`dtls`.`gst_name` AS `gst_name`,
	`dtls`.`gst_pc` AS `gst_pc`,
	`dtls`.`gst_amount` AS `gst_amount`,
	`dtls`.`is_tax1_included_in_gst` AS `is_tax1_included_in_gst`,
	`dtls`.`is_tax2_included_in_gst` AS `is_tax2_included_in_gst`,
	`dtls`.`is_tax3_included_in_gst` AS `is_tax3_included_in_gst`,
	`dtls`.`is_sc_included_in_gst` AS `is_sc_included_in_gst`,
	`dtls`.`sc_name` AS `sc_name`,
	`dtls`.`is_sc_applied` AS `is_sc_applied`,
	`dtls`.`sc_pc` AS `sc_pc`,
	`dtls`.`sc_amount` AS `sc_amount`,
	`dtls`.`item_total` AS `item_total`,
	`dtls`.`discount_type` AS `discount_type`,
	`dtls`.`discount_id` AS `discount_id`,
	`dtls`.`discount_code` AS `discount_code`,
	`dtls`.`discount_name` AS `discount_name`,
	`dtls`.`discount_description` AS `discount_description`,
	`dtls`.`discount_price` AS `discount_price`,
	`dtls`.`discount_is_percentage` AS `discount_is_percentage`,
	`dtls`.`discount_is_overridable` AS `discount_is_overridable`,
	`dtls`.`discount_is_item_specific` AS `discount_is_item_specific`,
	`dtls`.`discount_permitted_for` AS `discount_permitted_for`,
	`dtls`.`discount_is_promotion` AS `discount_is_promotion`,
	`dtls`.`discount_amount` AS `discount_amount`,
	`dtls`.`discount_variants` AS `discount_variants`,
	`dtls`.`discount_grouping_quantity` AS `discount_grouping_quantity`,
	`dtls`.`discount_allow_editing` AS `discount_allow_editing`,
	`dtls`.`round_adjustment` AS `round_adjustment`,
	`dtls`.`attrib1_name` AS `attrib1_name`,
	`dtls`.`attrib1_options` AS `attrib1_options`,
	`dtls`.`attrib2_name` AS `attrib2_name`,
	`dtls`.`attrib2_options` AS `attrib2_options`,
	`dtls`.`attrib3_name` AS `attrib3_name`,
	`dtls`.`attrib3_options` AS `attrib3_options`,
	`dtls`.`attrib4_name` AS `attrib4_name`,
	`dtls`.`attrib4_options` AS `attrib4_options`,
	`dtls`.`attrib5_name` AS `attrib5_name`,
	`dtls`.`attrib5_options` AS `attrib5_options`,
	`dtls`.`attrib1_selected_option` AS `attrib1_selected_option`,
	`dtls`.`attrib2_selected_option` AS `attrib2_selected_option`,
	`dtls`.`attrib3_selected_option` AS `attrib3_selected_option`,
	`dtls`.`attrib4_selected_option` AS `attrib4_selected_option`,
	`dtls`.`attrib5_selected_option` AS `attrib5_selected_option`,
	`dtls`.`status` AS `status`,
	`dtls`.`remarks` AS `remarks`,
	`dtls`.`is_void` AS `is_void`,
	`dtls`.`cashier_id` AS `cashier_id`,
	`dtls`.`order_date` AS `order_date`,
	`dtls`.`order_time` AS `order_time`,
	`dtls`.`serving_table_id` AS `serving_table_id`,
	`dtls`.`served_by` AS `served_by`,
	`dtls`.`service_type` AS `service_type`,
	`dtls`.`parent_dtl_id` AS `parent_dtl_id`,
	`dtls`.`has_choices` AS `has_choices`,
	`dtls`.`item_type` AS `item_type`,
	`dtls`.`sale_item_choices_id` AS `sale_item_choices_id`,
	`dtls`.`sale_item_choices_free_items` AS `sale_item_choices_free_items`,
	`dtls`.`sale_item_choices_max_items` AS `sale_item_choices_max_items`,
	`dtls`.`sale_item_choices_choice_id` AS `sale_item_choices_choice_id`,
	`dtls`.`sale_item_choices_choice_code` AS `sale_item_choices_choice_code`,
	`dtls`.`sale_item_choices_choice_name` AS `sale_item_choices_choice_name`,
	`dtls`.`sale_item_choices_choice_description` AS `sale_item_choices_choice_description`,
	`dtls`.`sale_item_choices_choice_is_global` AS `sale_item_choices_choice_is_global`,
	`dtls`.`combo_content_id` AS `combo_content_id`,
	`dtls`.`combo_content_code` AS `combo_content_code`,
	`dtls`.`combo_content_name` AS `combo_content_name`,
	`dtls`.`combo_content_description` AS `combo_content_description`,
	`dtls`.`combo_content_max_items` AS `combo_content_max_items`,
	`dtls`.`combo_content_uom_id` AS `combo_content_uom_id`,
	`dtls`.`seat_no` AS `seat_no`,
	`dtls`.`is_printed_to_kitchen` AS `is_printed_to_kitchen`,
	`dtls`.`kitchen_status` AS `kitchen_status`,
	`dtls`.`kitchen_print_status` AS `kitchen_print_status`,
	`dtls`.`kitchen_id` AS `kitchen_id`,
	`dtls`.`void_by` AS `void_by`,
	`dtls`.`void_at` AS `void_at`,
	`dtls`.`last_sync_at` AS `last_sync_at`,

IF (
	`hdrs`.`bill_discount_percentage`,
	round(
		(
			`dtls`.`tax1_amount` - (
				(
					`dtls`.`tax1_amount` * `hdrs`.`bill_discount_percentage`
				) / 100
			)
		),
		2
	),
	`dtls`.`tax1_amount`
) AS `actual_tax1_amount`,

IF (
	`hdrs`.`bill_discount_percentage`,
	round(
		(
			`dtls`.`tax2_amount` - (
				(
					`dtls`.`tax2_amount` * `hdrs`.`bill_discount_percentage`
				) / 100
			)
		),
		2
	),
	`dtls`.`tax2_amount`
) AS `actual_tax2_amount`,

IF (
	`hdrs`.`bill_discount_percentage`,
	round(
		(
			`dtls`.`tax3_amount` - (
				(
					`dtls`.`tax3_amount` * `hdrs`.`bill_discount_percentage`
				) / 100
			)
		),
		2
	),
	`dtls`.`tax3_amount`
) AS `actual_tax3_amount`,

IF (
	`hdrs`.`bill_discount_percentage`,
	round(
		(
			`dtls`.`item_total` - (
				(
					`dtls`.`item_total` * `hdrs`.`bill_discount_percentage`
				) / 100
			)
		),
		2
	),
	`dtls`.`item_total`
) AS `actual_item_total` 
 FROM
	(
		`order_dtls` `dtls`
		JOIN `order_hdrs` `hdrs` ON (
			(
				`hdrs`.`order_id` = `dtls`.`order_id`
			)
		)
	);

-- ----------------------------
-- View structure for v_order_dtls
-- ----------------------------

DROP VIEW IF EXISTS v_order_dtls;
CREATE VIEW v_order_dtls AS  
SELECT
(select 1 from order_dtls where ((order_dtls.parent_dtl_id = odt.id) and (order_dtls.item_type = 1)) limit 1) AS has_choice_items,
(select 1 from order_dtls where ((order_dtls.parent_dtl_id = odt.id) and (order_dtls.item_type = 2)) limit 1) AS has_combo_content_items,
odt.id AS id,
odt.order_id AS order_id,
odt.sale_item_id AS sale_item_id,
odt.sale_item_code AS sale_item_code,
odt.sale_item_hsn_code AS sale_item_hsn_code,
odt.sub_class_id AS sub_class_id,
odt.sub_class_code AS sub_class_code,
odt.sub_class_hsn_code AS sub_class_hsn_code,
odt.sub_class_name AS sub_class_name,
odt.name AS name,
odt.description AS description,
odt.alternative_name AS alternative_name,
sale_items.is_printable_to_kitchen AS is_printable_to_kitchen,
odt.name_to_print AS name_to_print,
odt.alternative_name_to_print AS alternative_name_to_print,
odt.qty AS qty,
odt.tray_code AS tray_code,odt.tray_weight AS tray_weight,
odt.uom_code AS uom_code,
odt.uom_name AS uom_name,
odt.uom_symbol AS uom_symbol,
odt.tax_calculation_method AS tax_calculation_method,
odt.is_open AS is_open,odt.is_combo_item AS is_combo_item,
odt.fixed_price AS fixed_price,
(case when (isnull(odt.rtls_price) or (odt.rtls_price = 0)) then 
	odt.fixed_price else odt.rtls_price end) AS rtls_price,
(case when (isnull(odt.whls_price) or (odt.whls_price = 0)) then 
	odt.fixed_price else odt.whls_price end) AS whls_price,
odt.is_whls_price_pc AS is_whls_price_pc,
odt.customer_price_variance AS customer_price_variance,
odt.tax_id AS tax_id,
odt.tax_code AS tax_code,
odt.tax_name AS tax_name,
odt.is_tax1_applied AS is_tax1_applied,
odt.tax1_name AS tax1_name,
odt.tax1_pc AS tax1_pc,
odt.tax1_amount AS tax1_amount,
odt.is_tax2_applied AS is_tax2_applied,
odt.tax2_name AS tax2_name,
odt.tax2_pc AS tax2_pc,
odt.tax2_amount AS tax2_amount,
odt.is_tax3_applied AS is_tax3_applied,
odt.tax3_name AS tax3_name,
odt.tax3_pc AS tax3_pc,
odt.tax3_amount AS tax3_amount,
odt.is_gst_applied AS is_gst_applied,
odt.gst_name AS gst_name,
odt.gst_pc AS gst_pc,
odt.gst_amount AS gst_amount,
odt.is_tax1_included_in_gst AS is_tax1_included_in_gst,
odt.is_tax2_included_in_gst AS is_tax2_included_in_gst,
odt.is_tax3_included_in_gst AS is_tax3_included_in_gst,
odt.is_sc_included_in_gst AS is_sc_included_in_gst,
odt.sc_name AS sc_name,
odt.is_sc_applied AS is_sc_applied,
odt.sc_pc AS sc_pc,
odt.sc_amount AS sc_amount,
odt.item_total AS item_total,
odt.discount_type AS discount_type,
odt.discount_id AS discount_id,
odt.discount_code AS discount_code,
odt.discount_name AS discount_name,
odt.discount_description AS discount_description,
odt.discount_price AS discount_price,
odt.discount_is_percentage AS discount_is_percentage,
odt.discount_is_overridable AS discount_is_overridable,
odt.discount_is_item_specific AS discount_is_item_specific,
odt.discount_permitted_for AS discount_permitted_for,
odt.discount_is_promotion AS discount_is_promotion,
odt.discount_amount AS discount_amount,
odt.discount_variants AS discount_variants,
odt.discount_grouping_quantity AS discount_grouping_quantity,
odt.discount_allow_editing AS discount_allow_editing,
odt.round_adjustment AS round_adjustment,
odt.attrib1_name AS attrib1_name,
odt.attrib1_options AS attrib1_options,
odt.attrib2_name AS attrib2_name,
odt.attrib2_options AS attrib2_options,
odt.attrib3_name AS attrib3_name,
odt.attrib3_options AS attrib3_options,
odt.attrib4_name AS attrib4_name,
odt.attrib4_options AS attrib4_options,
odt.attrib5_name AS attrib5_name,
odt.attrib5_options AS attrib5_options,
odt.attrib1_selected_option AS attrib1_selected_option,
odt.attrib2_selected_option AS attrib2_selected_option,
odt.attrib3_selected_option AS attrib3_selected_option,
odt.attrib4_selected_option AS attrib4_selected_option,
odt.attrib5_selected_option AS attrib5_selected_option,
odt.status AS status,
odt.remarks AS remarks,
odt.is_void AS is_void,
odt.is_printed_to_kitchen AS is_printed_to_kitchen,
odt.cashier_id AS cashier_id,
odt.order_date AS order_date,
odt.order_time AS order_time,
odt.serving_table_id AS serving_table_id,
odt.served_by AS served_by,
odt.service_type AS service_type,
odt.parent_dtl_id AS parent_dtl_id,
odt.has_choices AS has_choices,
odt.item_type AS item_type,
odt.sale_item_choices_id AS sale_item_choices_id,
odt.sale_item_choices_free_items AS sale_item_choices_free_items,
odt.sale_item_choices_max_items AS sale_item_choices_max_items,
odt.sale_item_choices_choice_id AS sale_item_choices_choice_id,
odt.sale_item_choices_choice_code AS sale_item_choices_choice_code,
odt.sale_item_choices_choice_name AS sale_item_choices_choice_name,
odt.sale_item_choices_choice_description AS sale_item_choices_choice_description,
odt.sale_item_choices_choice_is_global AS sale_item_choices_choice_is_global,
odt.combo_content_id AS combo_content_id,
odt.combo_content_code AS combo_content_code,
odt.combo_content_name AS combo_content_name,
odt.combo_content_description AS combo_content_description,
odt.combo_content_max_items AS combo_content_max_items,
odt.combo_content_uom_id AS combo_content_uom_id,
odt.seat_no AS seat_no,
odt.last_sync_at AS last_sync_at,
odt.void_at AS void_at,
odt.void_by AS void_by,
sale_items.item_thumb AS item_thumb,
sale_items.taxation_based_on AS taxation_based_on,
sale_items.best_before AS best_before,
sale_items.stock_item_id AS stock_item_id,
sale_items.barcode,
ifnull(item_classes.print_order,9999) AS print_order,
odt.kitchen_print_status AS kitchen_print_status,
odt.kitchen_id AS kitchen_id,
odt.tax_id_home_service AS tax_id_home_service,
odt.tax_id_table_service AS tax_id_table_service,
odt.tax_id_take_away_service AS tax_id_take_away_service,
odt.kitchen_status AS kitchen_status,
oref.id AS refund_id,
oref.paid_amount AS refund_amount,
oref.qty AS refund_qty,
oref.tax1_amount AS refund_tax1_amount,
oref.tax2_amount AS refund_tax2_amount,
oref.tax3_amount AS refund_tax3_amount,
oref.gst_amount AS refund_gst_amount,
oref.sc_amount as refund_sc_amount
from (order_dtls odt 
join sale_items on odt.sale_item_id = sale_items.id
JOIN item_classes on sale_items.sub_class_id=item_classes.id
left join order_refunds oref on odt.order_id = oref.order_id 
and odt.id = oref.order_dtl_id);

-- ----------------------------
-- View structure for v_order_hdrs
-- ----------------------------
DROP  VIEW IF EXISTS v_order_hdrs;
CREATE VIEW `v_order_hdrs` AS 
select 
	`hdr`.`invoice_prefix` AS `invoice_prefix`,
	concat(ifnull(`hdr`.`invoice_prefix`,''),
	convert(lpad(`hdr`.`invoice_no`,7,'0') using utf8)) AS `invoice_no_long`,
	`hdr`.`invoice_no` AS `invoice_no`,`order_queue`.`id` AS `queue_no`,
	`hdr`.`order_id` AS `order_id`,
	`hdr`.`order_no` AS `order_no`,
	`hdr`.`shop_code` AS `shop_code`,
	`hdr`.`station_code` AS `station_code`,
	`hdr`.`user_id` AS `user_id`,
	`hdr`.`order_date` AS `order_date`,
	`hdr`.`order_time` AS `order_time`,
	`hdr`.`shift_id` AS `shift_id`,
	`hdr`.`customer_id` AS `customer_id`,
	`hdr`.`is_ar_customer` AS `is_ar_customer`,
	`hdr`.`detail_total` AS `detail_total`,
	`hdr`.`total_tax1` AS `total_tax1`,
	`hdr`.`total_tax2` AS `total_tax2`,
	`hdr`.`total_tax3` AS `total_tax3`,
	`hdr`.`total_gst` AS `total_gst`,
	`hdr`.`total_sc` AS `total_sc`,
	`hdr`.`total_detail_discount` AS `total_detail_discount`,
	`hdr`.`final_round_amount` AS `final_round_amount`,
	`hdr`.`total_amount` AS `total_amount`,
	`hdr`.`total_amount_paid` AS `total_amount_paid`,
	`hdr`.`total_balance` AS `total_balance`,
	`hdr`.`actual_balance_paid` AS `actual_balance_paid`,
	`hdr`.`cash_out` AS `cash_out`,
	`hdr`.`remarks` AS `remarks`,
	`hdr`.`closing_date` AS `closing_date`,
	`hdr`.`closing_time` AS `closing_time`,
	`hdr`.`closed_by` AS `closed_by`,
	`hdr`.`status` AS `status`,
	`hdr`.`total_print_count` AS `total_print_count`,
	`hdr`.`refund_total_tax1` AS `refund_total_tax1`,
	`hdr`.`refund_total_tax2` AS `refund_total_tax2`,
	`hdr`.`refund_total_tax3` AS `refund_total_tax3`,
	`hdr`.`refund_total_gst` AS `refund_total_gst`,
	`hdr`.`refund_total_sc` AS `refund_total_sc`,
	`hdr`.`refund_amount` AS `refund_amount`,
	`hdr`.`sync_message` AS `sync_message`,
	`hdr`.`created_by` AS `created_by`,
	`hdr`.`created_at` AS `created_at`,
	`hdr`.`updated_by` AS `updated_by`,
	`hdr`.`updated_at` AS `updated_at`,
	`hdr`.`sync_status` AS `sync_status`,
	`hdr`.`bill_less_tax_amount` AS `bill_less_tax_amount`,
	`hdr`.`bill_discount_amount` AS `bill_discount_amount`,
	`hdr`.`serving_table_id` AS `serving_table_id`,
	`hdr`.`served_by` AS `served_by`,
	`hdr`.`service_type` AS `service_type`,
	`hdr`.`covers` AS `covers`,
	count(`dtl`.`id`) AS `item_count`,
	sum(`dtl`.`qty`) AS `total_qty`,
	`hdr`.`payment_process_status` AS `payment_process_status`,
	`hdr`.`is_locked` AS `is_locked`,
	`hdr`.`locked_station_code` AS `locked_station_code`,
	`hdr`.`locked_station_id` AS `locked_station_id`,
	`hdr`.`is_required_kitchen_print` AS `is_required_kitchen_print`,
	`hdr`.`is_required_bill_print` AS `is_required_bill_print`,
	`hdr`.`alias_text` AS `alias_text`,`hdr`.`driver_name` AS `driver_name`,
	`hdr`.`vehicle_number` AS `vehicle_number`,
	`hdr`.`void_by` AS `void_by`,
	`hdr`.`void_at` AS `void_at`,
	`hdr`.`bill_discount_percentage` AS `bill_discount_percentage`,
	`hdr`.`due_datetime` AS `due_datetime`,
	`hdr`.`order_medium` AS `order_medium`,
	`hdr`.`delivery_type` AS `delivery_type`,
	`hdr`.`extra_charges` AS `extra_charges`,
	`hdr`.`extra_charge_tax_id` AS `extra_charge_tax_id`,
	`hdr`.`extra_charge_tax_code` AS `extra_charge_tax_code`,
	`hdr`.`extra_charge_tax_name` AS `extra_charge_tax_name`,
	`hdr`.`extra_charge_tax1_name` AS `extra_charge_tax1_name`,
	`hdr`.`extra_charge_tax1_pc` AS `extra_charge_tax1_pc`,
	`hdr`.`extra_charge_tax1_amount` AS `extra_charge_tax1_amount`,
	`hdr`.`extra_charge_tax2_name` AS `extra_charge_tax2_name`,
	`hdr`.`extra_charge_tax2_pc` AS `extra_charge_tax2_pc`,
	`hdr`.`extra_charge_tax2_amount` AS `extra_charge_tax2_amount`,
	`hdr`.`extra_charge_tax3_name` AS `extra_charge_tax3_name`,
	`hdr`.`extra_charge_tax3_pc` AS `extra_charge_tax3_pc`,
	`hdr`.`extra_charge_tax3_amount` AS `extra_charge_tax3_amount`,
	`hdr`.`extra_charge_sc_name` AS `extra_charge_sc_name`,
	`hdr`.`extra_charge_sc_pc` AS `extra_charge_sc_pc`,
	`hdr`.`extra_charge_sc_amount` AS `extra_charge_sc_amount`,
	`hdr`.`extra_charge_gst_name` AS `extra_charge_gst_name`,
	`hdr`.`extra_charge_gst_pc` AS `extra_charge_gst_pc`,
	`hdr`.`extra_charge_gst_amount` AS `extra_charge_gst_amount`,
	`hdr`.`extra_charge_remarks` AS `extra_charge_remarks`,
	`order_customers`.`name` AS `name`,
	`order_customers`.`address` AS `address`,
	`order_customers`.`city` AS `city`,
	`order_customers`.`state` AS `state`,
	`order_customers`.`state_code` AS `state_code`,
	`order_customers`.`country` AS `country`,
	`order_customers`.`phone` AS `phone`,
	`order_customers`.`tin` AS `tin`,
	(select group_concat(`oss`.`seat_no` separator ',') from `order_serving_seats` `oss` where (`oss`.`order_id` = `hdr`.`order_id`)) AS `seat_nos`,
	ifnull((select 1 from `order_dtls` where ((`order_dtls`.`order_id` = `hdr`.`order_id`) and (`order_dtls`.`is_printed_to_kitchen` = 0) and 
	(`order_dtls`.`kitchen_id` is not null) and (`order_dtls`.`kitchen_id` <> 0)) limit 1),0) AS `has_non_printed_items` 
from ( `order_hdrs` `hdr` 
left join `order_dtls` `dtl` on `hdr`.`order_id` = `dtl`.`order_id`  and `dtl`.`is_void` = 0  
left join `order_queue` ON `hdr`.`order_id` = `order_queue`.`order_id` 
left join `order_customers` on `hdr`.`order_id` = `order_customers`.`order_id`)
where (`hdr`.`status` <> 0) 
group by `hdr`.`order_id`,`order_queue`.`id`;
 
	
-- ----------------------------
-- View structure for v_order_payments
-- ----------------------------
DROP VIEW IF EXISTS v_order_payments;
CREATE VIEW v_order_payments AS 
SELECT
	pos_invoice.id AS `invoice_no`,
	order_payments.order_id,
	order_payments.id,
	order_payments.order_payment_hdr_id,
	order_payments.payment_mode,
	order_payments.paid_amount,
	order_payments.card_name,
	order_payments.card_type,
	order_payments.card_no,
	order_payments.name_on_card,
	order_payments.card_expiry_month,
	order_payments.card_expiry_year,
	order_payments.card_approval_code,
	order_payments.card_account_type,
	order_payments.pos_customer_receipt,
	order_payments.pos_merchant_receipt,
	order_payments.company_id,
	order_payments.voucher_id,
	order_payments.voucher_value,
	order_payments.voucher_count,
	order_payments.cashier_id,
	order_payments.payment_date,
	order_payments.payment_time,
	order_payments.discount_id,
	order_payments.discount_code,
	order_payments.discount_name,
	order_payments.discount_description,
	order_payments.discount_price,
	order_payments.discount_is_percentage,
	order_payments.discount_is_overridable,
	order_payments.discount_amount,
	order_payments.is_repayment,
	order_payments.is_voucher_balance_returned,
	order_payments.partial_balance,
	order_payments.last_sync_at,
	order_payments.cashier_shift_id ,
	order_payment_hdr.is_advance,
	order_payments.created_at,
	order_payments.created_by,
	order_payments.updated_at,
	order_payments.updated_by	
FROM
	(
		order_payments
	  JOIN order_payment_hdr ON order_payments.order_payment_hdr_id=order_payment_hdr.id
		LEFT JOIN pos_invoice ON (
			order_payments.order_id = pos_invoice.order_id
		)
	);
	
-- ----------------------------
-- View structure for v_order_payment_hdr
-- ----------------------------
DROP VIEW IF EXISTS v_order_payment_hdr;
CREATE VIEW v_order_payment_hdr AS 
SELECT
	*
FROM
	order_payment_hdr ;

-- ----------------------------
-- View structure for v_order_serving_tables
-- ----------------------------
DROP VIEW IF EXISTS `v_order_serving_tables`;
CREATE VIEW `v_order_serving_tables` AS 
SELECT
	`v_serving_tables`.`code` AS `code`,
	`v_serving_tables`.`name` AS `name`,
	`v_serving_tables`.`description` AS `description`,
	`v_serving_tables`.`is_system` AS `is_system`,
	`v_serving_tables`.`is_valid` AS `is_valid`,
	`v_serving_tables`.`row_position` AS `row_position`,
	`v_serving_tables`.`column_position` AS `column_position`,
	`v_serving_tables`.`serving_table_location_id` AS `serving_table_location_id`,
	`v_serving_tables`.`layout_width` AS `layout_width`,
	`v_serving_tables`.`layout_height` AS `layout_height`,
	`v_serving_tables`.`layout_image` AS `layout_image`,
	`v_serving_tables`.`publish_status` AS `publish_status`,
	`v_serving_tables`.`is_deleted` AS `is_deleted`,
	`order_serving_tables`.`selected_seat_no` AS `selected_seat_no`,
	`order_serving_tables`.`is_selected` AS `is_selected`,
	`v_serving_tables`.`id` AS `id`,
	`order_serving_tables`.`order_id` AS `order_id`,
	`order_hdrs`.`status` AS `status`,
	`order_serving_tables`.`table_id` AS `serving_table_id`,
	`v_serving_tables`.`covers` AS `covers`
FROM
	(
		(
			`order_serving_tables`
			JOIN `v_serving_tables` ON (
				(
					`order_serving_tables`.`table_id` = `v_serving_tables`.`id`
				)
			)
		)
		JOIN `order_hdrs` ON (
			(
				`order_serving_tables`.`order_id` = `order_hdrs`.`order_id`
			)
		)
	)
WHERE
	(
		`order_serving_tables`.`is_void` = 0
	);
	
-- ----------------------------
-- View structure for v_order_refunds
-- ----------------------------
-- DROP VIEW IF EXISTS `v_order_refunds`;	
-- CREATE VIEW `v_order_refunds`AS 
-- select `hdr`.`invoice_prefix` AS `invoice_prefix`,concat(ifnull(`hdr`.`invoice_prefix`,''),lpad(`hdr`.`invoice_no`,7,'0')) AS `invoice_no_long`,`hdr`.`invoice_no` AS `invoice_no`,`order_queue`.`id` AS `queue_no`,`hdr`.`order_id` AS `order_id`,`hdr`.`order_no` AS `order_no`,`hdr`.`shop_code` AS `shop_code`,`hdr`.`station_code` AS `station_code`,`hdr`.`user_id` AS `user_id`,`hdr`.`order_date` AS `order_date`,`hdr`.`order_time` AS `order_time`,`hdr`.`shift_id` AS `shift_id`,`hdr`.`customer_id` AS `customer_id`,`hdr`.`is_ar_customer` AS `is_ar_customer`,`hdr`.`detail_base_total` AS `detail_base_total`,`hdr`.`detail_net_total` AS `detail_net_total`,`hdr`.`detail_total` AS `detail_total`,`hdr`.`total_tax1` AS `total_tax1`,`hdr`.`total_tax2` AS `total_tax2`,`hdr`.`total_tax3` AS `total_tax3`,`hdr`.`total_gst` AS `total_gst`,`hdr`.`total_sc` AS `total_sc`,`hdr`.`total_detail_discount` AS `total_detail_discount`,`hdr`.`final_round_amount` AS `final_round_amount`,`hdr`.`total_amount` AS `total_amount`,`hdr`.`total_amount_paid` AS `total_amount_paid`,`hdr`.`total_balance` AS `total_balance`,`hdr`.`actual_balance_paid` AS `actual_balance_paid`,`hdr`.`cash_out` AS `cash_out`,`hdr`.`remarks` AS `remarks`,`hdr`.`closing_date` AS `closing_date`,`hdr`.`closing_time` AS `closing_time`,`hdr`.`closed_by` AS `closed_by`,`hdr`.`status` AS `status`,`hdr`.`total_print_count` AS `total_print_count`,`hdr`.`sync_message` AS `sync_message`,`hdr`.`created_by` AS `created_by`,`hdr`.`created_at` AS `created_at`,`hdr`.`updated_by` AS `updated_by`,`hdr`.`updated_at` AS `updated_at`,`hdr`.`sync_status` AS `sync_status`,`hdr`.`bill_less_tax_amount` AS `bill_less_tax_amount`,`hdr`.`bill_discount_amount` AS `bill_discount_amount`,`hdr`.`serving_table_id` AS `serving_table_id`,`hdr`.`served_by` AS `served_by`,`hdr`.`service_type` AS `service_type`,`hdr`.`covers` AS `covers`,count(`dtl`.`id`) AS `item_count`,sum(`dtl`.`qty`) AS `total_qty`,`hdr`.`payment_process_status` AS `payment_process_status`,`hdr`.`is_locked` AS `is_locked`,`hdr`.`locked_station_code` AS `locked_station_code`,`hdr`.`locked_station_id` AS `locked_station_id`,`hdr`.`is_required_kitchen_print` AS `is_required_kitchen_print`,`hdr`.`is_required_bill_print` AS `is_required_bill_print`,`hdr`.`alias_text` AS `alias_text`,`hdr`.`driver_name` AS `driver_name`,`hdr`.`vehicle_number` AS `vehicle_number`,`hdr`.`void_by` AS `void_by`,`hdr`.`void_at` AS `void_at`,`hdr`.`bill_discount_percentage` AS `bill_discount_percentage`,`hdr`.`due_datetime` AS `due_datetime`,`hdr`.`order_medium` AS `order_medium`,`hdr`.`delivery_type` AS `delivery_type`,`hdr`.`extra_charges` AS `extra_charges`,`hdr`.`extra_charge_tax_id` AS `extra_charge_tax_id`,`hdr`.`extra_charge_tax_code` AS `extra_charge_tax_code`,`hdr`.`extra_charge_tax_name` AS `extra_charge_tax_name`,`hdr`.`extra_charge_tax1_name` AS `extra_charge_tax1_name`,`hdr`.`extra_charge_tax1_pc` AS `extra_charge_tax1_pc`,`hdr`.`extra_charge_tax1_amount` AS `extra_charge_tax1_amount`,`hdr`.`extra_charge_tax2_name` AS `extra_charge_tax2_name`,`hdr`.`extra_charge_tax2_pc` AS `extra_charge_tax2_pc`,`hdr`.`extra_charge_tax2_amount` AS `extra_charge_tax2_amount`,`hdr`.`extra_charge_tax3_name` AS `extra_charge_tax3_name`,`hdr`.`extra_charge_tax3_pc` AS `extra_charge_tax3_pc`,`hdr`.`extra_charge_tax3_amount` AS `extra_charge_tax3_amount`,`hdr`.`extra_charge_gst_name` AS `extra_charge_gst_name`,`hdr`.`extra_charge_gst_pc` AS `extra_charge_gst_pc`,`hdr`.`extra_charge_gst_amount` AS `extra_charge_gst_amount`,`hdr`.`extra_charge_remarks` AS `extra_charge_remarks`,`order_customers`.`name` AS `name`,`order_customers`.`address` AS `address`,`order_customers`.`city` AS `city`,`order_customers`.`state` AS `state`,`order_customers`.`state_code` AS `state_code`,`order_customers`.`country` AS `country`,`order_customers`.`phone` AS `phone`,`order_customers`.`tin` AS `tin`,`order_refunds`.`paid_amount` AS `refund_amount`,`order_refunds`.`tax1_amount` AS `refund_total_tax1`,`order_refunds`.`tax2_amount` AS `refund_total_tax2`,`order_refunds`.`tax3_amount` AS `refund_total_tax3`,`order_refunds`.`gst_amount` AS `refund_total_gst` from ((((`order_hdrs` `hdr` left join `order_dtls` `dtl` on(((`hdr`.`order_id` = `dtl`.`order_id`) and (`dtl`.`is_void` = 0)))) left join `order_refunds` on((`hdr`.`closing_date` = `order_refunds`.`refund_date`))) left join `order_queue` on((`hdr`.`order_id` = `order_queue`.`order_id`))) left join `order_customers` on((`hdr`.`order_id` = `order_customers`.`order_id`))) where (`hdr`.`status` <> 0) group by `hdr`.`order_id`,`order_queue`.`id` ;
	
-- ----------------------------
-- View structure for v_serving_tables_ext
-- ----------------------------
DROP VIEW IF EXISTS `v_serving_tables_ext`;
CREATE VIEW `v_serving_tables_ext` AS 
SELECT
	`v_serving_tables`.`id` AS `id`,
	`v_serving_tables`.`code` AS `code`,
	`v_serving_tables`.`name` AS `name`,
	`v_serving_tables`.`description` AS `description`,
	`v_serving_tables`.`is_system` AS `is_system`,
	`v_serving_tables`.`is_valid` AS `is_valid`,
	`v_serving_tables`.`covers` AS `covers`,
	`v_serving_tables`.`row_position` AS `row_position`,
	`v_serving_tables`.`column_position` AS `column_position`,
	`v_serving_tables`.`serving_table_location_id` AS `serving_table_location_id`,
	`v_serving_tables`.`status` AS `status`,
	`v_serving_tables`.`created_by` AS `created_by`,
	`v_serving_tables`.`created_at` AS `created_at`,
	`v_serving_tables`.`updated_by` AS `updated_by`,
	`v_serving_tables`.`updated_at` AS `updated_at`,
	`v_serving_tables`.`publish_status` AS `publish_status`,
	`v_serving_tables`.`is_deleted` AS `is_deleted`,
	`v_serving_tables`.`is_synchable` AS `is_synchable`,
	(
		SELECT
			count(0)
		FROM
			`v_order_serving_tables` `order_serving_tables`
		WHERE
			(
				(
					`order_serving_tables`.`serving_table_id` = `v_serving_tables`.`id`
				)
				AND `order_serving_tables`.`order_id` IN (
					SELECT
						`order_hdrs`.`order_id`
					FROM
						`order_hdrs`
					WHERE
						(
							(
								`order_hdrs`.`order_id` = `order_serving_tables`.`order_id`
							)
							AND (
								`order_hdrs`.`status` IN (1, 2, 6) AND `order_hdrs`.`service_type` = 3
							)
						)
				)
			)
	) AS `order_count`,
	`v_serving_tables`.`layout_width` AS `layout_width`,
	`v_serving_tables`.`layout_height` AS `layout_height`,
	`v_serving_tables`.`layout_image` AS `layout_image`,
	`v_serving_tables`.`serving_table_location_code` AS `serving_table_location_code`,
	`v_serving_tables`.`serving_table_location_name` AS `serving_table_location_name`,
	(
		select group_concat(`v_order_hdrs`.`served_by` separator ',') 
		from `v_order_hdrs`
	 	where `v_order_hdrs`.`serving_table_id` = `v_serving_tables`.`id`  and  
		      `v_order_hdrs`.`status` = 1  and `v_order_hdrs`.`has_non_printed_items` = 1 ) AS `non_printed_order_waiters` 
from `v_serving_tables`;

-- ----------------------------
-- View structure for v_orders_and_combo_contents
-- ----------------------------
DROP VIEW IF EXISTS `v_orders_and_combo_contents`;
CREATE  VIEW `v_orders_and_combo_contents` AS select `dtl`.`order_date` AS `order_date`,`dtl`.`sale_item_id` AS `sale_item_id`,sum(`dtl`.`qty`) AS `dtl_qty`,`dtl`.`uom_symbol` AS `saleitem_uom_code`,`dtl`.`is_combo_item` AS `is_combo_item`,`cmb`.`content_item_id` AS `combo_item_id`,`cmb`.`qty` AS `combo_qty`,`um`.`uom_symbol` AS `combo_item_uom_code`,`sl`.`stock_item_id` AS `stock_item_id`,`stk`.`uom_id` AS `stk_uom_id`,`um`.`uom_symbol` AS `stk_uom_code`,`slt`.`stock_item_id` AS `combo_item_stock_item_id`,`cmbstk`.`uom_id` AS `combo_item_stock_item_uom_id`,`um1`.`uom_symbol` AS `combo_item_stock_item_uom_code`,sum(((((`dtl`.`tax1_amount` + `dtl`.`tax2_amount`) + `dtl`.`tax3_amount`) + `dtl`.`gst_amount`) + `dtl`.`sc_amount`)) AS `tax_amount`,sum(`dtl`.`item_total`) AS `item_total`,sum(`dtl`.`discount_amount`) AS `discount_amount`,sum(`dtl`.`round_adjustment`) AS `round_adjustment` from (((((((`order_dtls` `dtl` left join `order_combo_contents` `cmb` on(((`dtl`.`order_id` = `cmb`.`order_id`) and (`dtl`.`is_void` <> 1) and (`dtl`.`id` = `cmb`.`order_dtl_id`) and (`dtl`.`sale_item_id` = `cmb`.`combo_sale_item_id`)))) join `sale_items` `sl` on(((`sl`.`id` = `dtl`.`sale_item_id`) and (`sl`.`is_group_item` = 0) and (`sl`.`is_deleted` = 0)))) left join `sale_items` `slt` on(((`slt`.`id` = `cmb`.`content_item_id`) and (`sl`.`is_group_item` = 0) and (`sl`.`is_deleted` = 0)))) join `stock_items` `stk` on(((`stk`.`id` = `sl`.`stock_item_id`) and (`stk`.`is_deleted` = 0)))) join `uoms` `um` on(((`um`.`id` = `stk`.`uom_id`) and (`um`.`is_deleted` = 0)))) left join `stock_items` `cmbstk` on(((`cmbstk`.`id` = `slt`.`stock_item_id`) and (`cmbstk`.`is_deleted` = 0)))) left join `uoms` `um1` on(((`um1`.`id` = `cmbstk`.`uom_id`) and (`um1`.`is_deleted` = 0)))) group by `dtl`.`order_date`,`dtl`.`sale_item_id`,`dtl`.`is_combo_item`,`cmb`.`content_item_id` ;

-- ----------------------------
-- View structure for v_order_dtl_combo_contents
-- ----------------------------
DROP VIEW IF EXISTS `v_order_dtl_combo_contents`;
CREATE  VIEW `v_order_dtl_combo_contents` AS select `order_dtls`.`id` AS `order_dtl_id`,`order_dtls`.`combo_content_id` AS `id`,`order_dtls`.`combo_content_code` AS `CODE`,`order_dtls`.`combo_content_name` AS `NAME`,`order_dtls`.`combo_content_description` AS `description`,`order_dtls`.`combo_content_max_items` AS `max_items`,`order_dtls`.`combo_content_uom_id` AS `uom_id`,`order_dtls`.`sale_item_id` AS `sale_item_id` from `order_dtls` where (`order_dtls`.`item_type` = 2) ;

-- ----------------------------
-- View structure for v_stock_sale_items
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_sale_items`;
CREATE  VIEW `v_stock_sale_items` AS select (case `stock_items`.`is_sellable` when 1 then `sale_items`.`id` else `stock_items`.`id` end) AS `id`,`stock_items`.`is_sellable` AS `is_sellable`,`stock_items`.`id` AS `stock_item_id`,`sale_items`.`is_group_item` AS `is_group_item`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`code` else `stock_items`.`code` end) AS `code`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`name` else `stock_items`.`name` end) AS `name`,`sale_items`.`is_open` AS `is_open`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`tax_id` else `stock_items`.`tax_id` end) AS `taxid`,(case `stock_items`.`is_sellable` when 1 then (select `taxes`.`name` from `taxes` where (`taxes`.`id` = `sale_items`.`tax_id`)) else (select `taxes`.`name` from `taxes` where (`taxes`.`id` = `stock_items`.`tax_id`)) end) AS `tax_name`,`sale_items`.`group_item_id` AS `group_item_id`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`description` else `stock_items`.`description` end) AS `description`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`is_manufactured` else `stock_items`.`is_manufactured` end) AS `is_manufactured`,`stock_items`.`prd_department_id` AS `prd_department_id`,`sale_items`.`profit_category_id` AS `profit_category_id`,`stock_items`.`stock_item_category_id` AS `stock_item_category_id`,`sale_items`.`sub_class_id` AS `sub_class_id`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`is_valid` else `stock_items`.`is_valid` end) AS `is_valid`,`sale_items`.`alternative_name` AS `alternative_name`,`sale_items`.`name_to_print` AS `name_to_print`,`sale_items`.`alternative_name_to_print` AS `alternative_name_to_print`,`sale_items`.`barcode` AS `barcode`,`sale_item_ext`.`kitchen_id` AS `kitchen_id`,ifnull((select `kitchens`.`name` from `kitchens` where (`kitchens`.`id` = `sale_item_ext`.`kitchen_id`)),'--') AS `kitchen_name`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`uom_id` else `stock_items`.`uom_id` end) AS `uom_id`,`sale_items`.`item_cost` AS `item_cost`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`fixed_price` else `stock_items`.`unit_price` end) AS `fixed_price`,(case `stock_items`.`is_sellable` when 1 then `sale_items`.`tax_calculation_method` else `stock_items`.`tax_calculation_method` end) AS `tax_calculation_method`,`sale_items`.`is_synchable` AS `is_synchable`,`sale_items`.`is_require_weighing` AS `is_require_weighing`,`sale_items`.`display_order` AS `display_order`,`sale_items`.`is_hot_item_1` AS `is_hot_item_1`,`sale_items`.`hot_item_1_display_order` AS `hot_item_1_display_order`,`sale_items`.`is_hot_item_2` AS `is_hot_item_2`,`sale_items`.`hot_item_2_display_order` AS `hot_item_2_display_order`,`sale_items`.`is_hot_item_3` AS `is_hot_item_3`,`sale_items`.`hot_item_3_display_order` AS `hot_item_3_display_order`,`sale_items`.`attrib1_name` AS `attrib1_name`,`sale_items`.`attrib1_options` AS `attrib1_options`,`sale_items`.`attrib2_name` AS `attrib2_name`,`sale_items`.`attrib2_options` AS `attrib2_options`,`sale_items`.`attrib3_name` AS `attrib3_name`,`sale_items`.`attrib3_options` AS `attrib3_options`,`sale_items`.`attrib4_name` AS `attrib4_name`,`sale_items`.`attrib4_options` AS `attrib4_options`,`sale_items`.`attrib5_name` AS `attrib5_name`,`sale_items`.`attrib5_options` AS `attrib5_options`,`sale_items`.`created_by` AS `created_by`,`sale_items`.`created_at` AS `created_at`,`sale_items`.`fg_color` AS `fg_color`,`sale_items`.`bg_color` AS `bg_color`,`sale_items`.`updated_by` AS `updated_by`,`sale_items`.`updated_at` AS `updated_at`,`sale_items`.`is_combo_item` AS `is_combo_item`,`sale_items`.`taxation_based_on` AS `taxation_based_on`,`sale_items`.`choice_ids` AS `choice_ids`,`sale_items`.`tax_id_home_service` AS `tax_id_home_service`,`sale_items`.`tax_id_table_service` AS `tax_id_table_service`,`sale_items`.`tax_id_take_away_service` AS `tax_id_take_away_service`,`sale_items`.`hsn_code` AS `hsn_code`,`sale_items`.`whls_price` AS `whls_price`,`sale_items`.`is_whls_price_pc` AS `is_whls_price_pc`,`sale_items`.`item_thumb` AS `item_thumb`,`sale_items`.`item_weight` AS `item_weight`,`item_classes`.`name` AS `item_name`,`stock_items`.`stock_uom_id` AS `stock_uom_id` from (((`stock_items` left join `sale_items` on((`sale_items`.`stock_item_id` = `stock_items`.`id`))) left join `item_classes` on((`sale_items`.`sub_class_id` = `item_classes`.`id`))) left join `sale_item_ext` on((`sale_items`.`id` = `sale_item_ext`.`id`))) where (case `stock_items`.`is_sellable` when 1 then (`sale_items`.`is_deleted` = 0) else (`stock_items`.`is_deleted` = 0) end) order by (case `stock_items`.`is_sellable` when 1 then `sale_items`.`id` else `stock_items`.`id` end) desc ;

-- ----------------------------
-- View structure for v_shop_shifts
-- ----------------------------
DROP VIEW IF EXISTS `v_shop_shifts`;
CREATE   VIEW `v_shop_shifts` AS 

SELECT shop_shifts.*, IFNULL((
SELECT 1
FROM v_cashier_shift
WHERE (v_cashier_shift.shift_id= shop_shifts.id AND closing_time IS NULL)
LIMIT 1),0) AS is_open
FROM shop_shifts ;




-- ----------------------------
-- View structure for v_shop_departments
-- ----------------------------
DROP VIEW IF EXISTS `v_shop_departments`;
CREATE   VIEW `v_shop_departments` AS 
select `departments`.`id` AS `id`,`departments`.`code` AS `code`,`departments`.`name` AS `name`,
`departments`.`description` AS `description`,`departments`.`sales_account_code` AS `sales_account_code`,
`departments`.`purchase_account_code` AS `purchase_account_code`,
`departments`.`stock_account_code` AS `stock_account_code`,
`departments`.`cogs_account_code` AS `cogs_account_code`,
`departments`.`wages_account_code` AS `wages_account_code`,
`departments`.`gst_collected_account_code` AS `gst_collected_account_code`,
`departments`.`gst_paid_account_code` AS `gst_paid_account_code`,`departments`.`created_by` AS `created_by`,
`departments`.`created_at` AS `created_at`,`departments`.`updated_by` AS `updated_by`,
`departments`.`updated_at` AS `updated_at`,`departments`.`publish_status` AS `publish_status`,
`departments`.`is_deleted` AS `is_deleted`,`departments`.`is_system` AS `is_system`,
`departments`.`is_synchable` AS `is_synchable`,`departments`.`last_sync_at` AS `last_sync_at`,
ifnull((select 1 from `shop_departments` where ((`shop_departments`.`department_id` = `departments`.`id`) and (`shop_departments`.`is_deleted` = 0)) limit 1),0) AS `shop_id` 
from `departments`;



-- ----------------------------
-- Procedure structure for getInvoiceNo
-- ----------------------------
DROP FUNCTION IF EXISTS getInvoiceNo;
DELIMITER ;;
CREATE FUNCTION `getInvoiceNo`(orderid VARCHAR (50),
	addInvoice INT) RETURNS int(11)
	DETERMINISTIC
BEGIN
	DECLARE 	nId INTEGER ;
	
		SET nId = IFNULL((SELECT	id FROM	pos_invoice	WHERE order_id = orderid COLLATE 'utf8_unicode_ci'),	0) ;
		IF (nId = 0 AND addInvoice = 1) THEN
				INSERT INTO pos_invoice (order_id) SELECT	orderid ;
				SET nId = (	SELECT	id	FROM pos_invoice WHERE order_id = orderid COLLATE 'utf8_unicode_ci') ;
			 
				UPDATE order_hdrs SET invoice_no=nId WHERE order_id = orderid COLLATE 'utf8_unicode_ci' ;
		END 	IF ;

		RETURN nId;
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getOrderQueueNo
-- ----------------------------
DROP FUNCTION IF EXISTS getOrderQueueNo;
DELIMITER ;;
CREATE FUNCTION `getOrderQueueNo`(orderid VARCHAR(50)) RETURNS int(11)
DETERMINISTIC
BEGIN
	DECLARE
		nId INTEGER;


SET nId = IFNULL(
	(
		SELECT
			id
		FROM
			order_queue
		WHERE
			order_id = orderid COLLATE 'utf8_unicode_ci'
	),
	0
);


IF (nId = 0) THEN
	INSERT INTO order_queue (order_id) SELECT
		orderid;


SET nId = (
	SELECT
		id
	FROM
		order_queue
	WHERE
		order_id = orderid COLLATE 'utf8_unicode_ci'
);

END
IF;
RETURN nId;
END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for sp_resetOrderQueueNumber
-- ----------------------------
DROP PROCEDURE IF EXISTS  sp_resetOrderQueueNumber;
DELIMITER ;;
CREATE  PROCEDURE `sp_resetOrderQueueNumber`()
DETERMINISTIC
BEGIN

  SET @max_queue_no = 0;

  SET @delSQL = "DELETE from order_queue where id NOT in (SELECT queue_no from v_order_hdrs where status in (1,6));";
  PREPARE delStmt FROM @delSQL;
  EXECUTE delStmt;
  DEALLOCATE PREPARE delStmt;

  SET @statement = "SELECT ifnull(max(id),0)+1 INTO @max_queue_no FROM order_queue;";
  PREPARE stmt FROM @statement;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;

  SET @resetSQL = CONCAT("ALTER TABLE order_queue AUTO_INCREMENT = ",@max_queue_no,";");
  PREPARE resetStmt FROM @resetSQL;
  EXECUTE resetStmt;

  DEALLOCATE PREPARE resetStmt;


END  ;;

DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_copy_holidays_to_nextyear
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_copy_holidays_to_nextyear`;
DELIMITER ;;
CREATE  PROCEDURE `sp_copy_holidays_to_nextyear`(IN `cur_year` INT,IN `nextyear` INT)
DETERMINISTIC
BEGIN

 declare n_month  INT; 
 declare n_date  INT;
 declare creason varchar(255);
 declare noMoreRows integer;

 START TRANSACTION;


 BEGIN
  declare curs cursor for SELECT hd_month,hd_date,reason FROM holidays WHERE hd_year=cur_year and is_deleted=0;
  declare continue handler for not found set noMoreRows = 1;
  set noMoreRows = 0;
  open curs;
  myLoop:loop
    fetch curs into n_month,n_date,creason;
    if noMoreRows then
      leave myLoop;
    end if;
	INSERT INTO `holidays` (`hd_year`, `hd_month`, `hd_date`, `reason`, `publish_status`, `is_deleted`,
		 `is_synchable`) VALUES ( nextyear, n_month, n_date, creason, 0, 0, 1);
	
  end loop myLoop;
  close curs; 
 END;
 COMMIT;	

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_dashboard_sales_schedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_dashboard_sales_schedule`;
DELIMITER ;;
CREATE  PROCEDURE `sp_dashboard_sales_schedule`(IN `c_date` datetime,IN `cshop_id` int,c_weeknumber int,
csales_status int,cur_time time)
DETERMINISTIC
BEGIN
 declare n_tamount  FLOAT;
 declare n_extraamount  FLOAT;
 declare n_total_detail_discount FLOAT;
 declare n_total_bill_discount_amt FLOAT;
 declare n_total_refund_amt FLOAT;
 declare n_bill_discount_tax FLOAT;
 declare n_refund_tax FLOAT;
 declare n_extra_tax FLOAT;

 
 declare n_defined_weekly_target  FLOAT;
 declare n_item_sold_today  FLOAT;
 declare n_order_trans_today  FLOAT;
 declare n_total_tax_today  FLOAT;
 declare n_staff_hrs_today  VARCHAR(20);
 declare n_staff_hrs_target   VARCHAR(20);
 declare n_labour_cost_today  FLOAT;
 declare n_labour_cost_target  FLOAT;
 declare nCounter integer;
 declare noMoreRows integer;

 START TRANSACTION;
  BEGIN


	SET nCounter=(SELECT count(sales_id) as sales_count from  dashboard_sales where sales_date=c_date);
	SET  n_tamount=(SELECT sum(detail_total)
				 as total_today  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);
    SET  n_extraamount=(SELECT sum(IFNULL(extra_charges,0))
				 as total_extras  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);
    SET n_total_detail_discount = (SELECT sum(total_detail_discount)
				 as total_discounttoday  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);
    SET n_total_bill_discount_amt = (SELECT sum(bill_discount_amount)
				 as total_billDiscount_amt  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);
   /* SET n_total_refund_amt= (SELECT sum(paid_amount)
				 as total_refund_amt_today  FROM `order_refunds` WHERE  refund_date=c_date);*/
   SET n_total_refund_amt= (SELECT sum(paid_amount)
				 as total_refund_amt_today  FROM `order_payments` WHERE  payment_date=c_date AND is_repayment=1);
    SET n_bill_discount_tax= (SELECT sum((total_tax1+total_tax2+total_tax3+IFNULL(extra_charge_tax1_amount,0)+  IFNULL(extra_charge_tax2_amount,0)+  IFNULL(extra_charge_tax3_amount,0))*bill_discount_percentage/100)
				 as total_bill_discount_tax  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);
   
      /*SET n_bill_discount_tax= (SELECT round(sum(total_tax1+IFNULL(extra_charge_tax1_amount,0))*bill_discount_percentage/100)+round(sum(total_tax2+IFNULL(extra_charge_tax2_amount,0))*bill_discount_percentage/100)+round(sum(total_tax3+IFNULL(extra_charge_tax3_amount,0))*bill_discount_percentage/100)
                           
				 as total_bill_discount_tax  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);*/



/*SET n_refund_tax=(SELECT sum(tax1_amount+tax2_amount+tax3_amount)
				 as total_refund_tax_today  FROM `order_refunds` WHERE  refund_date=c_date);*/
    SET n_refund_tax=(SELECT sum(total_tax1+total_tax2+total_tax3)
				 as total_refund_tax_today  FROM `order_payment_hdr` WHERE  payment_date=c_date AND is_refund=1 );
    SET  n_extra_tax=(SELECT sum(IFNULL(extra_charge_tax1_amount,0)+IFNULL(extra_charge_tax2_amount,0))
				 as total_extras_tax  FROM `order_hdrs` hdr 	WHERE  closing_date=c_date);
     
     

  IF(nCounter=0) THEN
 	BEGIN
  	declare curs cursor for SELECT IFNULL(target_amount,0) as target_amount,IFNULL(labour_hour,0) as labour_hour,
		IFNULL(labour_cost,0) as labour_cost  FROM `v_shop_target_amount` WHERE 
		shop_id= cshop_id and week_no=c_weeknumber and year=YEAR(c_date);
 	declare continue handler for not found set noMoreRows = 1;
  	set noMoreRows = 0;
  	open curs;
 	  myLoop:loop
    		fetch curs into n_defined_weekly_target,n_staff_hrs_target,n_labour_cost_target;
    		if noMoreRows then
      			leave myLoop;
    		end if;
		
	  end loop myLoop;
  	close curs; 
 END;
END IF;
 
 SET  n_item_sold_today=(SELECT sum(qty) as qty  FROM `order_hdrs` hdr inner join order_dtls  dtl 
	on hdr.order_id=dtl.order_id  and  dtl.is_void !=1 WHERE hdr.order_date=c_date);

 SET  n_order_trans_today=(SELECT count(order_id) as order_id  FROM `order_hdrs` hdr WHERE hdr.order_date=c_date);
 SET n_total_tax_today=(SELECT sum((total_tax1+total_tax2+total_tax3+total_gst+total_sc)) as total_tax  FROM 
		`order_hdrs` hdr  WHERE hdr.closing_date=c_date);
 SET  n_staff_hrs_today=(select SEC_TO_TIME(SUM(TIME_TO_SEC( 
				TIMEDIFF(IFNULL(concat(txn.shift_end_date,' ', txn.shift_end_time),concat(txn.shift_start_date,' ',cur_time)),
				concat(txn.shift_start_date,' ', txn.shift_start_time))  
				)))  as  time_diff from txn_staff_attendance  txn 
				where  txn.shift_start_date =c_date);

 SET  n_labour_cost_today=(select sum(time_diff) from ( select ord.employee_id,round(cost_per_hour* SUM(TIME_TO_SEC( 

				TIMEDIFF(IFNULL(concat(txn.shift_end_date,' ', txn.shift_end_time),concat(txn.shift_start_date,' ',cur_time)),
				concat(txn.shift_start_date,' ', txn.shift_start_time)) 
				))/3600,2) as  time_diff  from txn_staff_attendance  txn 
                            inner join (select distinct(usr.employee_id),cost_per_hour from users usr  
                               inner join employees emp on 
				emp.id=usr.employee_id  ) 
                              ord on ord. employee_id=txn.employee_id and txn.shift_start_date=c_date 
				 group by employee_id ) as total_hours); 

  BEGIN

	
	IF (nCounter=0) THEN	
 	insert dashboard_sales(sales_date,sales_amount,sales_target,item_sold,transactions,staff_hours,staff_hours_target,
			labour_cost,labour_cost_target,total_tax,sales_status,total_extras,
			total_detail_discount,total_bill_discount,total_refund_amt,total_bill_discount_tax,refund_tax,total_extracharge_tax)
	 		select c_date,IFNULL(n_tamount,0),IFNULL(n_defined_weekly_target,0), IFNULL(n_item_sold_today,0),
			IFNULL(n_order_trans_today,0),IFNULL(n_staff_hrs_today,'00:00:00'),IFNULL(n_staff_hrs_target,'00:00:00'),
			IFNULL(n_labour_cost_today,0),IFNULL(n_labour_cost_target,0),IFNULL(n_total_tax_today,0),csales_status,
            IFNULL(n_extraamount,0),IFNULL(n_total_detail_discount,0),
            IFNULL(n_total_bill_discount_amt,0),IFNULL(n_total_refund_amt,0),IFNULL(n_bill_discount_tax,0),IFNULL(n_refund_tax,0),IFNULL(n_extra_tax,0);
	ELSE
	update dashboard_sales set sales_amount=IFNULL(n_tamount,0),item_sold=IFNULL(n_item_sold_today,0),
			transactions=IFNULL(n_order_trans_today,0),staff_hours=IFNULL(n_staff_hrs_today,'00:00:00'),
			labour_cost=IFNULL(n_labour_cost_today,0),sales_status=csales_status,
			total_tax=IFNULL(n_total_tax_today,0),total_extras=IFNULL(n_extraamount,0),total_detail_discount=IFNULL(n_total_detail_discount,0),
            total_bill_discount=IFNULL(n_total_bill_discount_amt,0),total_refund_amt=IFNULL(n_total_refund_amt,0),
            total_bill_discount_tax=IFNULL(n_bill_discount_tax,0),refund_tax=IFNULL(n_refund_tax,0),total_extracharge_tax=IFNULL(n_extra_tax,0)where sales_date=c_date;
	END IF;
		
  END;
	
   END;
  COMMIT;	
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_tally_report
-- ----------------------------
DROP PROCEDURE IF EXISTS sp_tally_report;
DELIMITER ;;
CREATE PROCEDURE `sp_tally_report`(IN `from_date` DATE,
IN `to_date` DATE,
IN `filelocation` VARCHAR (250))
BEGIN
DROP TEMPORARY TABLE
IF EXISTS tally_export_orders;

CREATE TEMPORARY TABLE tally_export_orders (
invoice_no VARCHAR (50),
invoice_date DATE,
voucher_type VARCHAR (50),
customer_name VARCHAR (250),
address_line1 VARCHAR (250),
address_line2 VARCHAR (250),
gst_reg_type VARCHAR (250),
gst_party_type VARCHAR (250),
gst_no VARCHAR (250),
state VARCHAR (250),
country VARCHAR (250),
bank_name VARCHAR (50),
bank_acc_no VARCHAR (50),
bank_ifsc VARCHAR (50),
date_of_supply DATE,
vehicle_no VARCHAR (50),
driver_name VARCHAR (50),
item_name VARCHAR (250),
item_group VARCHAR (100),
addl_ledger VARCHAR (100),
tax_classification VARCHAR (50),
item_cess_per VARCHAR (10),
description VARCHAR (255),
hsn_code VARCHAR (50),
taxability VARCHAR (250),
quantity DECIMAL (14, 5),
unit VARCHAR (10),
uom_uqc VARCHAR (250),
rate DECIMAL (14, 5),
value DECIMAL (14, 5),
-- discount_amnt DECIMAL (14, 5),
-- bill_amnt DECIMAL (14, 5),
discount_per DECIMAL (14, 5),
discount DECIMAL (14, 5),
taxable DECIMAL (14, 5),
tax DECIMAL (14, 5),
cgst DECIMAL (14, 5),
sgst DECIMAL (14, 5),
igst DECIMAL (14, 5),
l_cgst DECIMAL (14, 5),
l_sgst DECIMAL (14, 5),
l_igst DECIMAL (14, 5),
round_off DECIMAL (14, 5),
net_value DECIMAL (14, 5),
final_round_amount DECIMAL (14, 5),
cess DECIMAL (14, 5),
l_cess DECIMAL (14, 5),
tax_group VARCHAR (250)
);


BEGIN
DECLARE
v_id VARCHAR (150);

DECLARE
v_order_id VARCHAR (50);

DECLARE
v_order_no INT (11);

DECLARE
v_invoice_no INT (11);

DECLARE
v_invoice_prefix VARCHAR (50);

DECLARE
v_voucher_type VARCHAR (50);

DECLARE
v_cus_name VARCHAR (250);

DECLARE
v_address VARCHAR (250);

DECLARE
v_city VARCHAR (250);

DECLARE
v_gst_reg_type VARCHAR (250);

DECLARE
v_gst_party_type VARCHAR (250);

DECLARE
v_gst_number VARCHAR (250);

DECLARE
v_state VARCHAR (250);

DECLARE
v_country VARCHAR (250);

DECLARE
v_closing_date DATE;

DECLARE
v_name VARCHAR (250);

DECLARE
v_itemgroup VARCHAR (100);

DECLARE
v_description VARCHAR (250);

DECLARE
v_sub_class_hsn_code VARCHAR (50);

DECLARE
v_taxability VARCHAR (250);

DECLARE
v_qty DECIMAL (14, 5);

DECLARE
v_uom_symbol VARCHAR (10);

DECLARE
v_uom_uqc VARCHAR (250);

DECLARE
v_tax_calculation_method INT (2);

DECLARE
v_fixed_price DECIMAL (14, 5);

DECLARE
v_item_total DECIMAL (14, 5);

DECLARE
v_discount_price DECIMAL (14, 5);

DECLARE
v_discount_is_percentage INT (11);

DECLARE
v_discount_amount DECIMAL (14, 5);

DECLARE
v_tax_per DECIMAL (14, 5);

DECLARE
v_bill_discount_percentage DECIMAL (14, 5);

DECLARE
v_tax1_pc DECIMAL (14, 5);

DECLARE
v_tax2_pc DECIMAL (14, 5);

DECLARE
v_tax3_pc DECIMAL (14, 5);

DECLARE
v_addl_ledger VARCHAR (100);

DECLARE
v_tax_classification DECIMAL (14, 5);

DECLARE
v_extracharge_row INT (11);

DECLARE
v_final_round_amount DECIMAL (14, 5);

DECLARE
v_tax_group VARCHAR (250);

DECLARE
endrow INTEGER DEFAULT 0;

DECLARE
orders CURSOR FOR SELECT
dtls.id,
hdr.order_id,
hdr.order_no,
hdr.invoice_no,
hdr.invoice_prefix,
(
CASE
WHEN hdr. STATUS = 3
OR hdr. STATUS = 4 THEN
(
SELECT
sales_voucher
FROM
tally_vouchers
)
ELSE
''
END
) AS voucher_type,
IFNULL(oc. NAME, '') AS cus_name,
IFNULL(oc.address, ''),
IFNULL(oc.city, ''),
IFNULL(reg. NAME, ''),
IFNULL(party. NAME, ''),
IFNULL(oc.tin, '') AS gst_number,
IFNULL(oc.state, ''),
IFNULL(oc.country, ''),
hdr.closing_date,
dtls. NAME,
dtls.sub_class_name AS 'group',
IFNULL(dtls.description, ''),
IFNULL(dtls.sub_class_hsn_code, ''),
IF (
tax. CODE = 'NOTAX',
'Exempt',
'Taxable'
),
dtls.qty,
dtls.uom_symbol,
IFNULL(tallyuom. NAME, 'OTH-OTHERS'),
dtls.tax_calculation_method,
dtls.fixed_price,
dtls.item_total,
dtls.discount_price,
dtls.discount_is_percentage,
dtls.discount_amount,
IFNULL(tax.tax1_percentage, 0) + IFNULL(tax.tax2_percentage, 0) + IFNULL(tax.tax3_percentage, 0) AS tax_per,
hdr.bill_discount_percentage,

IF (
dtls.is_tax1_applied = 1,
dtls.tax1_pc,
0
) AS tax1_pc,
IF (
dtls.is_tax2_applied = 1,
dtls.tax2_pc,
0
) AS tax2_pc,
IF (
dtls.is_tax3_applied = 1,
dtls.tax3_pc,
0
) AS tax3_pc,
 '' AS addl_ledger,
 '0' AS tax_classification,
 '0' AS extracharge_row,
 hdr.final_round_amount,
 (IF(tax2.is_tax1_applicable=1,tax2.tax1_percentage,0)+IF(tax2.is_tax2_applicable=1,tax2.tax2_percentage,0)+IF(tax2.is_tax3_applicable=1,tax2.tax3_percentage,0)+IF(tax2.is_define_gst=1,tax2.gst_percentage,0)) AS tax_group
FROM
order_hdrs hdr
LEFT JOIN order_dtls dtls ON hdr.order_id = dtls.order_id
LEFT JOIN order_customers oc ON hdr.order_id = oc.order_id
LEFT JOIN taxes tax ON dtls.tax_id = tax.id
LEFT JOIN gst_reg_type reg ON oc.gst_reg_type = reg.id
LEFT JOIN gst_party_type party ON oc.gst_party_type = party.id
LEFT JOIN uoms uom ON dtls.uom_code = uom.code
LEFT JOIN tally_uom_mapping tallyuom ON uom.code = tallyuom.code
LEFT JOIN sale_items ON dtls.sale_item_id = sale_items.id
LEFT JOIN taxes tax2 ON sale_items.tax_group_id = tax2.id
WHERE
closing_date BETWEEN from_date
AND to_date
AND dtls.is_void = 0
UNION ALL
SELECT
'' AS id,
hdr.order_id,
hdr.order_no,
hdr.invoice_no,
hdr.invoice_prefix,
(
CASE
WHEN hdr. STATUS = 3
OR hdr. STATUS = 4 THEN
(
SELECT
sales_voucher
FROM
tally_vouchers
)
ELSE
''
END
) AS voucher_type,
IFNULL(oc. NAME, '') AS cus_name,
IFNULL(oc.address, ''),
IFNULL(oc.city, ''),
IFNULL(reg. NAME, ''),
IFNULL(party. NAME, ''),
IFNULL(oc.tin, '') AS gst_number,
IFNULL(oc.state, ''),
IFNULL(oc.country, ''),
hdr.closing_date,
'' AS NAME,
'' AS 'group',
'' AS description,
'' AS hsn_code,
'' AS taxcode,
'0' AS qty,
'' AS uom_symbol,
'' AS uom,
'0' AS tax_calculation_method,
hdr.extra_charges,
'0' AS item_total,
'0' AS discount_price,
'0' AS discount_is_percentage,
'0' AS discount_amount,
(
hdr.extra_charge_tax1_pc + hdr.extra_charge_tax2_pc + hdr.extra_charge_tax3_pc
) AS tax_classification,
hdr.bill_discount_percentage,
hdr.extra_charge_tax1_pc AS tax1_pc,
hdr.extra_charge_tax2_pc AS tax2_pc,
hdr.extra_charge_tax3_pc AS tax3_pc,
(
SELECT
addl_ledger
FROM
tally_vouchers
) AS addl_ledger,
(
hdr.extra_charge_tax1_pc + hdr.extra_charge_tax2_pc + hdr.extra_charge_tax3_pc
) AS tax_classification,
'1' AS extracharge_row,
hdr.final_round_amount,
'' AS tax_group
FROM
order_hdrs hdr
LEFT JOIN order_customers oc ON hdr.order_id = oc.order_id
LEFT JOIN gst_reg_type reg ON oc.gst_reg_type = reg.id
LEFT JOIN gst_party_type party ON oc.gst_party_type = party.id
WHERE
closing_date BETWEEN from_date
AND to_date
AND extra_charges <> 0
OR extra_charges <> NULL
ORDER BY
invoice_no ASC;

DECLARE
CONTINUE HANDLER FOR NOT FOUND
SET endrow = 1;
SET @invoiceno = 0;
OPEN orders;

START TRANSACTION;

getOrders :
LOOP
FETCH orders INTO v_id,
v_order_id,
v_order_no,
v_invoice_no,
v_invoice_prefix,
v_voucher_type,
v_cus_name,
v_address,
v_city,
v_gst_reg_type,
v_gst_party_type,
v_gst_number,
v_state,
v_country,
v_closing_date,
v_name,
v_itemgroup,
v_description,
v_sub_class_hsn_code,
v_taxability,
v_qty,
v_uom_symbol,
v_uom_uqc,
v_tax_calculation_method,
v_fixed_price,
v_item_total,
v_discount_price,
v_discount_is_percentage,
v_discount_amount,
v_tax_per,
v_bill_discount_percentage,
v_tax1_pc,
v_tax2_pc,
v_tax3_pc,
v_addl_ledger,
v_tax_classification,
v_extracharge_row,
v_final_round_amount,
v_tax_group;

IF endrow THEN
LEAVE getOrders;
END IF;

IF v_tax_classification = 0 THEN
SET @taxclass = v_tax_classification;
ELSE

SET @taxclass = CONCAT(
ROUND(v_tax_classification, 2),
'%'
);
END IF;

IF v_extracharge_row = 0 THEN

IF v_tax_calculation_method = 0 THEN
SET @rate = v_fixed_price / (1 + v_tax_per / 100);
ELSE
SET @rate = v_fixed_price;
END IF;

SET @VALUE = @rate * v_qty;

IF v_discount_is_percentage = 1 THEN
SET @discount = @VALUE * v_discount_price / 100;
ELSE
SET @discount = v_discount_amount - (v_discount_amount * v_tax_per / 100);
END IF;

SET @taxable_amnt = @VALUE -@discount;
SET @bill_discount = @taxable_amnt * v_bill_discount_percentage / 100;
SET @actual_taxable = @taxable_amnt -@bill_discount;
SET @actual_discount = @discount;
SET @bill_discount = @bill_discount;
SET @discount_per = 100 * @discount/@VALUE;
SET @invoice_discount = @bill_discount;
SET @tax1 = @actual_taxable * v_tax1_pc / 100;
SET @tax2 = @actual_taxable * v_tax2_pc / 100;
SET @tax3 = @actual_taxable * v_tax3_pc / 100;
SET @l_cgst = 0;
SET @l_sgst = 0;
SET @l_igst = 0;
IF v_tax3_pc = 0 THEN
SET @item_cess_per = 0;
ELSE 
SET @item_cess_per = CONCAT(ROUND(v_tax3_pc,2),'%');
END IF;
SET @calTotal = @actual_taxable +@tax1 +@tax2 +@tax3;
SET @netValue = v_item_total - (
v_item_total * v_bill_discount_percentage / 100
);
SET @roundVal = @netValue -@calTotal;
SET @netValueRounded = @calTotal +@roundVal;
ELSE
SET @rate = 0;
SET @VALUE = v_fixed_price;
SET @actual_discount = @VALUE * v_bill_discount_percentage / 100;
SET @bill_discount = 0;
SET @discount_per = 0;
SET @invoice_discount = @VALUE * v_bill_discount_percentage / 100;
SET @actual_taxable = @VALUE -@invoice_discount;
SET @tax1 = 0;
SET @tax2 = 0;
SET @tax3 = 0;
SET @item_cess_per = 0;
SET @l_cgst = @actual_taxable * v_tax1_pc / 100;
SET @l_sgst = @actual_taxable * v_tax2_pc / 100;
SET @l_igst = @actual_taxable * v_tax3_pc / 100;
SET @roundVal = 0;
SET @netValueRounded = @actual_taxable +@l_cgst +@l_sgst +@l_igst;
END IF;

SET @tempround = v_final_round_amount;
IF @invoiceno<>0 AND @invoiceno = v_invoice_no  THEN
SET @round_off = 0;
ELSE 
SET @round_off = v_final_round_amount;
END IF;

SET @invoiceno = v_invoice_no;

IF @invoice_discount > 0 THEN
SET @invoice_discount = CONCAT('-' ,@invoice_discount);
ELSE
SET @invoice_discount = 0;
END IF;

IF (v_invoice_prefix IS NULL) THEN
SET @invoiceno = v_invoice_no;
ELSE
SET @invoiceno = CONCAT(
v_invoice_prefix,
LPAD(v_invoice_no, 7, '0')
);
END IF;

BEGIN
INSERT INTO tally_export_orders (
`invoice_no`,
`invoice_date`,
`voucher_type`,
`customer_name`,
`address_line1`,
`address_line2`,
`gst_reg_type`,
`gst_party_type`,
`gst_no`,
`state`,
`country`,
`bank_name`,
`bank_acc_no`,
`bank_ifsc`,
`date_of_supply`,
`vehicle_no`,
`driver_name`,
`item_name`,
`item_group`,
`addl_ledger`,
`tax_classification`,
`item_cess_per`,
`description`,
`hsn_code`,
`taxability`,
`quantity`,
`unit`,
`uom_uqc`,
`rate`,
`value`,
/*`discount_amnt`,
`bill_amnt`,*/
`discount_per`,
`discount`,
`taxable`,
`tax`,
`cgst`,
`sgst`,
`igst`,
`l_cgst`,
`l_sgst`,
`l_igst`,
`round_off`,
`net_value`,
`final_round_amount`,
`cess`,
`l_cess`,
`tax_group`
)
VALUES
(
@invoiceno,
v_closing_date,
v_voucher_type,
v_cus_name,
REPLACE(v_address,'\r\n',' '),
v_city,
v_gst_reg_type,
v_gst_party_type,
v_gst_number,
v_state,
v_country,
'',
'',
'',
v_closing_date,
'',
'',
v_name,
v_itemgroup,
v_addl_ledger,
@taxclass,
@item_cess_per,
v_description,
v_sub_class_hsn_code,
v_taxability,
v_qty,
v_uom_symbol,
v_uom_uqc,
ROUND(@rate, 2),
ROUND(@VALUE, 2),
-- ROUND(@actual_discount, 2),
-- ROUND(@bill_discount, 2),
ROUND(@discount_per, 2),
ROUND(@invoice_discount, 2),
ROUND(@actual_taxable, 2),
v_tax_per,
ROUND(@tax1, 2),
ROUND(@tax2, 2),
'0',
ROUND(@l_cgst, 2),
ROUND(@l_sgst, 2),
'0',
ROUND(@roundVal, 2),
ROUND(@netValueRounded, 2),
ROUND(@round_off,2),
ROUND(@tax3, 2),
ROUND(@l_igst, 2),
v_tax_group
);

END;

END
LOOP
getOrders;

COMMIT;

CLOSE orders;

END;

BEGIN
DECLARE
v_id VARCHAR (150);

DECLARE
v_order_id VARCHAR (50);

DECLARE
v_order_no INT (11);

DECLARE
v_invoice_no INT (11);

DECLARE
v_invoice_prefix VARCHAR (50);

DECLARE
v_voucher_type VARCHAR (50);

DECLARE
v_cus_name VARCHAR (250);

DECLARE
v_address VARCHAR (250);

DECLARE
v_city VARCHAR (250);

DECLARE
v_gst_reg_type VARCHAR (250);

DECLARE
v_gst_party_type VARCHAR (250);

DECLARE
v_gst_number VARCHAR (250);

DECLARE
v_state VARCHAR (250);

DECLARE
v_country VARCHAR (250);

DECLARE
v_closing_date DATE;

DECLARE
v_name VARCHAR (250);

DECLARE
v_itemgroup VARCHAR (100);

DECLARE
v_description VARCHAR (250);

DECLARE
v_sub_class_hsn_code VARCHAR (50);

DECLARE
v_taxability VARCHAR (250);

DECLARE
v_qty DECIMAL (14, 5);

DECLARE
v_uom_symbol VARCHAR (10);

DECLARE
v_uom_uqc VARCHAR (250);

DECLARE
v_tax_calculation_method INT (2);

DECLARE
v_fixed_price DECIMAL (14, 5);

DECLARE
v_item_total DECIMAL (14, 5);

DECLARE
v_discount_price DECIMAL (14, 5);

DECLARE
v_discount_is_percentage INT (11);

DECLARE
v_discount_amount DECIMAL (14, 5);

DECLARE
v_tax_per DECIMAL (14, 5);

DECLARE
v_bill_discount_percentage DECIMAL (14, 5);

DECLARE
v_tax1_pc DECIMAL (14, 5);

DECLARE
v_tax2_pc DECIMAL (14, 5);

DECLARE
v_tax3_pc DECIMAL (14, 5);

DECLARE
v_addl_ledger VARCHAR (100);

DECLARE
v_tax_classification DECIMAL (14, 5);

DECLARE
v_tax_group VARCHAR (250);

DECLARE
endrowrefund INTEGER DEFAULT 0;

DECLARE
refund CURSOR FOR SELECT
dtls.id,
hdr.order_id,
hdr.order_no,
hdr.invoice_no,
hdr.invoice_prefix,
(
SELECT
refund_voucher
FROM
tally_vouchers
) AS voucher_type,
oc. NAME AS cus_name,
IFNULL(oc.address, ''),
IFNULL(oc.city, ''),
IFNULL(reg. NAME, ''),
IFNULL(party. NAME, ''),
IFNULL(oc.tin, '') AS gst_number,
IFNULL(oc.state, ''),
IFNULL(oc.country, ''),
hdr.closing_date,
dtls. NAME,
dtls.sub_class_name AS 'group',
IFNULL(dtls.description, ''),
IFNULL(dtls.sub_class_hsn_code, ''),
IF (
tax. CODE = 'NOTAX',
tax. CODE,
'Taxable'
),
IFNULL(refund.qty, 0),
dtls.uom_symbol,
IFNULL(tallyuom. NAME, 'OTH-OTHERS'),
dtls.tax_calculation_method,
dtls.fixed_price,
refund.paid_amount,
dtls.discount_price,
dtls.discount_is_percentage,
dtls.discount_amount,
IFNULL(tax.tax1_percentage, 0) + IFNULL(tax.tax2_percentage, 0) + IFNULL(tax.tax3_percentage, 0) AS tax_per,
hdr.bill_discount_percentage,
IFNULL(refund.tax1_amount, 0),
IFNULL(refund.tax2_amount, 0),
IFNULL(refund.tax3_amount, 0),
'' AS addl_ledger,
'0' AS tax_classification,
(IF(tax2.is_tax1_applicable=1,tax2.tax1_percentage,0)+IF(tax2.is_tax2_applicable=1,tax2.tax2_percentage,0)+IF(tax2.is_tax3_applicable=1,tax2.tax3_percentage,0)+IF(tax2.is_define_gst=1,tax2.gst_percentage,0)) AS tax_group
FROM
order_hdrs hdr
LEFT JOIN order_dtls dtls ON hdr.order_id = dtls.order_id
LEFT JOIN order_customers oc ON hdr.order_id = oc.order_id
LEFT JOIN taxes tax ON dtls.tax_id = tax.id
LEFT JOIN gst_reg_type reg ON oc.gst_reg_type = reg.id
LEFT JOIN gst_party_type party ON oc.gst_party_type = party.id
LEFT JOIN uoms uom ON dtls.uom_code = uom. CODE
LEFT JOIN tally_uom_mapping tallyuom ON uom. CODE = tallyuom. CODE
RIGHT JOIN order_refunds refund ON dtls.id = refund.order_dtl_id
LEFT JOIN sale_items ON dtls.sale_item_id = sale_items.id
LEFT JOIN taxes tax2 ON sale_items.tax_group_id = tax2.id
WHERE
closing_date BETWEEN from_date
AND to_date
AND dtls.is_void = 0
ORDER BY
invoice_no ASC;

DECLARE
CONTINUE HANDLER FOR NOT FOUND
SET endrowrefund = 1;

-- SET endrow = 0;
OPEN refund;

START TRANSACTION;

getOrderRefund :
LOOP
FETCH refund INTO v_id,
v_order_id,
v_order_no,
v_invoice_no,
v_invoice_prefix,
v_voucher_type,
v_cus_name,
v_address,
v_city,
v_gst_reg_type,
v_gst_party_type,
v_gst_number,
v_state,
v_country,
v_closing_date,
v_name,
v_itemgroup,
v_description,
v_sub_class_hsn_code,
v_taxability,
v_qty,
v_uom_symbol,
v_uom_uqc,
v_tax_calculation_method,
v_fixed_price,
v_item_total,
v_discount_price,
v_discount_is_percentage,
v_discount_amount,
v_tax_per,
v_bill_discount_percentage,
v_tax1_pc,
v_tax2_pc,
v_tax3_pc,
v_addl_ledger,
v_tax_classification,
v_tax_group;

IF endrowrefund THEN
LEAVE getOrderRefund;
END IF;

SET @rate = (v_item_total - (v_tax1_pc + v_tax2_pc + v_tax3_pc)) / v_qty;
SET @VALUE = @rate * v_qty;
SET @actual_discount = 0;
SET @discount_per = 0;
SET @actual_taxable  = @VALUE;
SET @tax1 = v_tax1_pc;
SET @tax2 = v_tax2_pc;
SET @tax3 = v_tax3_pc / 100;
SET @netValue = v_item_total;
SET @roundVal = 0;

IF (v_invoice_prefix IS NULL) THEN
SET @invoiceno = v_invoice_no;
ELSE
SET @invoiceno = CONCAT(
v_invoice_prefix,
LPAD(v_invoice_no, 7, '0')
);
END IF;

BEGIN
INSERT INTO tally_export_orders (
`invoice_no`,
`invoice_date`,
`voucher_type`,
`customer_name`,
`address_line1`,
`address_line2`,
`gst_reg_type`,
`gst_party_type`,
`gst_no`,
`state`,
`country`,
`bank_name`,
`bank_acc_no`,
`bank_ifsc`,
`date_of_supply`,
`vehicle_no`,
`driver_name`,
`item_name`,
`item_group`,
`addl_ledger`,
`tax_classification`,
`item_cess_per`,
`description`,
`hsn_code`,
`taxability`,
`quantity`,
`unit`,
`uom_uqc`,
`rate`,
`value`,
-- `discount_amnt`,
-- `bill_amnt`,
`discount_per`,
`discount`,
`taxable`,
`tax`,
`cgst`,
`sgst`,
`igst`,
`l_cgst`,
`l_sgst`,
`l_igst`,
`round_off`,
`net_value`,
`final_round_amount`,
`cess`,
`l_cess`,
`tax_group`
)
VALUES
(
@invoiceno,
v_closing_date,
v_voucher_type,
v_cus_name,
REPLACE(v_address,'\r\n',' '),
v_city,
v_gst_reg_type,
v_gst_party_type,
v_gst_number,
v_state,
v_country,
'',
'',
'',
v_closing_date,
'',
'',
v_name,
v_itemgroup,
'',
'0',
'0',
v_description,
v_sub_class_hsn_code,
v_taxability,
v_qty,
v_uom_symbol,
v_uom_uqc,
ROUND(@rate, 2),
ROUND(@VALUE, 2),
-- ROUND(@actual_discount, 2),
-- ROUND('0', 2),
ROUND(@discount_per, 2),
ROUND(@actual_discount, 2),
ROUND(@actual_taxable, 2),
v_tax_per,
ROUND(@tax1, 2),
ROUND(@tax2, 2),
'0',
'0',
'0',
'0',
ROUND(@roundVal, 2),
ROUND(@netValue, 2),
'0',
ROUND(@tax3, 2),
'0',
v_tax_group
);

END;

END
LOOP
getOrderRefund;

COMMIT;

CLOSE refund;

END;

SET @export = CONCAT(
'SELECT \'INVOICE NO\'\,\'INVOICE DATE\'\,\'VOUCHER TYPE\'\,\'CUSTOMER NAME\'\,\'ADDRESS LINE 1\'\,\'ADDRESS LINE 2\'\,\'GST Reg.TYPE\'\,\'PARTY TYPE\'\,\'GST NUMBER\'\,\'STATE\'\,\'COUNTRY\'\,\'BANK NAME\'\,\'BANK ACC NO\'\,\'BANK IFSC\'\,\'DATE OF SUPPLY\'\,\'VECHICLE NO\'\,\'DRIVER NAME\'\,\'ITEM NAME\'\,\'GROUP\'\,\'Addl Ledger Name\'\,\'Tax Classification\'\,\'Item CESS %\'\,\'DESCRIPTION\'\,\'HSN CODE\'\,\'TAXABILITY\'\,\'QUANTITY\'\,\'UNIT\'\,\'UQC\'\,\'RATE\'\,\'VALUE\'\,\'ITEM DISCOUNT %\'\,\'INVOICE DISCOUNT\'\,\'TAXABLE\'\,\'TAX\'\,\'Item CGST\'\,\'Item SGST\'\,\'Item IGST\'\,\'Ledger CGST\'\,\'Ledger SGST\'\,\'Ledger IGST\'\,\'ROUND OFF\'\,\'NET VALUE\',\'INVOICE ROUND OFF\',\'Item CESS\',\'Ledger CESS\',\'Tax Group\' UNION ALL',
' SELECT * FROM',
' tally_export_orders',
' INTO OUTFILE \'',
filelocation,
'\' FIELDS TERMINATED BY \',\' ENCLOSED BY \'"\' LINES TERMINATED BY \'\n\''
);

PREPARE stmnt
FROM
@export;

EXECUTE stmnt;

DEALLOCATE PREPARE stmnt;

SELECT
COUNT(invoice_no) AS invoice
FROM
tally_export_orders;

END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_dashboard_weekly_sales_schedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_dashboard_weekly_sales_schedule`;
DELIMITER ;;
CREATE  PROCEDURE `sp_dashboard_weekly_sales_schedule`(IN `c_date` datetime,IN `n_shop_id` INT,
IN n_weeknumber int,IN `c_curr_date` datetime,IN c_start_week_date datetime,IN `c_end_week_date` datetime,
IN n_curr_weeknumber int,IN `c_curr_start_week_date` datetime,IN c_curr_end_week_date datetime)
BEGIN
 declare n_sales_amount  FLOAT;
 declare n_defined_sales_target  FLOAT;
 declare n_item_sold  FLOAT;
 declare n_order_trans INT;
 declare n_staff_hrs VARCHAR(20);
 declare n_staff_hrs_target   VARCHAR(20);
 declare n_labour_cost  FLOAT;
 declare n_labour_cost_target  FLOAT;
 declare n_total_tax  FLOAT;
 declare nCounter integer;
 declare noMoreRows integer;
 declare cWeekStartDate datetime;
 declare nWeekCounter integer;
 declare nStatus integer;
 START TRANSACTION;
  BEGIN
	SET cWeekStartDate=c_start_week_date;
	SET nWeekCounter=0;
	
	DELETE FROM `weekly_dashboard_sales` WHERE start_date  between c_start_week_date AND c_curr_end_week_date;
	WHILE c_start_week_date <= c_curr_start_week_date DO
	
	if c_start_week_date < c_curr_start_week_date then
      			SET nStatus=1;
	else 
			SET nStatus=0;
    	end if;	
	
	if c_start_week_date= DATE_ADD(cWeekStartDate, INTERVAL nWeekCounter DAY) then
		BEGIN
      			SET n_weeknumber=n_weeknumber+1;
			SET nWeekCounter=nWeekCounter+7;
		END;
    	end if;

	BEGIN	
   declare curs cursor for SELECT sum(sales_amount+total_extras+total_extracharge_tax)-sum(total_refund_amt+total_detail_discount+total_bill_discount) as sales_amount,sum(sales_target) as sales_target,sum(item_sold) as item_sold,
		sum(transactions) as transactions,SEC_TO_TIME( SUM( TIME_TO_SEC( staff_hours ) ) )  as staff_hours,
		SEC_TO_TIME( SUM( TIME_TO_SEC(staff_hours_target) ) ) as staff_hours_target,
		sum(labour_cost) as labour_cost,sum(labour_cost_target) as labour_cost_target,
		sum(total_tax) as total_tax from dashboard_sales 
		WHERE sales_date between c_start_week_date and c_end_week_date ;
   declare continue handler for not found set noMoreRows = 1;
      set noMoreRows = 0;
      open curs;
      myLoop:loop
         fetch curs into  n_sales_amount, n_defined_sales_target,n_item_sold,n_order_trans,
			n_staff_hrs,n_staff_hrs_target,	n_labour_cost ,n_labour_cost_target, n_total_tax ;
            if noMoreRows then
               leave myLoop;
            end if;
	
	insert weekly_dashboard_sales(week_no,start_date,end_date,`sales_amount`,`sales_target`,`item_sold`,`transactions`,`staff_hours`,
			`staff_hours_target`,`labour_cost` , `labour_cost_target`, `total_tax`,sales_status ) 
	 		select n_weeknumber,c_start_week_date,c_end_week_date,IFNULL(n_sales_amount,0),
			IFNULL(n_defined_sales_target,0), IFNULL(n_item_sold,0),
			IFNULL(n_order_trans,0),IFNULL(n_staff_hrs,'00:00:00'),IFNULL(n_staff_hrs_target,'00:00:00'),
			IFNULL(n_labour_cost,0),IFNULL(n_labour_cost_target,0),IFNULL(n_total_tax,0),nStatus;
	end loop myLoop;
     	close curs; 
	END;
	Set c_start_week_date = DATE_ADD(c_start_week_date,INTERVAL 7 DAY);
	Set c_end_week_date = DATE_ADD(c_start_week_date,INTERVAL 6 DAY);
      END WHILE;	
  END;
 COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_delete_records
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_delete_records`;
DELIMITER ;;
CREATE  PROCEDURE `sp_delete_records`(IN `in1` int,IN `in2` VARCHAR(50))
BEGIN
		DECLARE variable1 INT;
		DECLARE variable2 VARCHAR(50) ;
		SET variable1 = in1;  
		SET variable2 = in2;
		CASE variable1

						when 0 then

									delete from cashier_shifts where  auto_id=variable2;

						when 1 then

								BEGIN    

										delete  from order_combo_contents where  order_id=variable2 ;
										delete  from order_customers where  order_id=variable2 ;
										delete  from order_discounts where  order_id=variable2 ;
										delete  from order_payments where  order_id=variable2 ;
										delete  from order_payment_hdr where  order_id=variable2 ;
										delete  from order_dtls where  order_id=variable2 ;
										delete  from order_hdrs where  order_id=variable2 ;
										delete  from pos_invoice where  order_id=variable2; 

								END;    
						when 2 then

								delete from shift_summary where  auto_id=variable2;
						ELSE    

								delete  from txn_staff_attendance where  id=variable2;    

		END CASE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_insert_sales_amount_for_hq
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_insert_sales_amount_for_hq`;
DELIMITER ;;
CREATE  PROCEDURE `sp_insert_sales_amount_for_hq`(IN `cshop_id` int,
IN `ndefined_weekly_target` FLOAT,IN `c_date` datetime,
IN `ncurrent_day_target_amount` FLOAT, IN `nweekly_amount` FLOAT,IN `nmonthly_amount` FLOAT,IN `nyearly_amount` FLOAT,
IN `nlastyear_sameday_target_amount` FLOAT, IN `nlastyear_weekly_amount` FLOAT,IN `nlastyear_yearly_amount` FLOAT)
BEGIN
 START TRANSACTION;
 BEGIN
 	DELETE FROM sales_amount_target where shop_id=cshop_id;
	insert into sales_amount_target(shop_id,defined_weekly_target,last_calculated_date,current_day_target_amount,weekly_amount,
					monthly_amount,yearly_amount,lastyear_sameday_target_amount,lastyear_weekly_amount,
					lastyear_yearly_amount )
	values(cshop_id,ndefined_weekly_target,c_date,ncurrent_day_target_amount ,nweekly_amount,nmonthly_amount ,
		 nyearly_amount,nlastyear_sameday_target_amount,nlastyear_weekly_amount,nlastyear_yearly_amount); 
	
 END;
 COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_omit_columns_from_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_omit_columns_from_table`;
DELIMITER ;;
CREATE PROCEDURE `sp_omit_columns_from_table`(IN `col_names` VARCHAR(255), IN `tbl_name` VARCHAR(255), IN `sync_at` VARCHAR(255),IN `schema_name` VARCHAR(255))
BEGIN
SET SESSION group_concat_max_len = 1000000;
SET @SQL = CONCAT('SELECT ', (SELECT GROUP_CONCAT(CONCAT("`", COLUMN_NAME, "`")) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME =tbl_name AND NOT FIND_IN_SET(COLUMN_NAME, col_names)
 AND TABLE_SCHEMA = schema_name),  CONCAT(' FROM ', tbl_name),  CONCAT(' where last_sync_at>= "',sync_at,'"'));
PREPARE stmt FROM @sql; 
EXECUTE stmt;
END;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for sp_order_delete
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_order_delete`;
DELIMITER ;;
CREATE  PROCEDURE `sp_order_delete`(IN `order_hdr` varchar(50))
BEGIN
	if exists(select count(order_id) from order_hdrs where order_id=order_hdr >0)
	THEN
       		BEGIN    
			delete  from order_combo_contents where  order_id=order_hdr;
           		delete  from order_payments where  order_id=order_hdr;
			delete  from order_discounts where  order_id=order_hdr;
           		delete  from order_dtls where  order_id=order_hdr ;
           		delete  from order_hdrs where  order_id=order_hdr ;
	       END ;
	END IF;    
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_reset_shop_table_auto_increment
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_reset_shop_table_auto_increment`;
DELIMITER ;;
CREATE  PROCEDURE `sp_reset_shop_table_auto_increment`(IN `db_name` varchar(100), IN `init_counter` int)
BEGIN
 declare tbl_name varchar(100);
 declare noMoreRows integer; 
 START TRANSACTION;
BEGIN
  declare curs cursor for  SELECT table_name FROM information_schema.tables WHERE table_schema =`db_name` AND TABLE_NAME IN 
			(SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema=`db_name` 
				AND Auto_increment IS NOT NULL AND TABLE_NAME NOT IN
				('bill_params','counter','global_roundings','system_params','txn_purchase_return_dtls','txn_po_template_dtls',
				'txn_stk_dtls','txn_stk_out_dtls','txn_stock_adjustment_dtls','txn_supplier_invoice_hdrs','txn_supplier_invoice_dtls','txn_stock_disposals','txn_supplier_invoice_sub_dtls',
				'tax_param','txn_po_dtls','txn_rfp_dtls','txn_staff_leave','system_functions','txn_system_counter','txn_dr_days','txn_dr_details','txn_dr_weeks'));
  declare continue handler for not found set noMoreRows = 1;
  set noMoreRows = 0;
  open curs;
  myLoop:loop
    fetch curs into tbl_name;
    if noMoreRows then
      leave myLoop;
    end if;
	
	SET @sql_text = concat('ALTER TABLE ',db_name,'.',tbl_name  ,' AUTO_INCREMENT =', init_counter);
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt; 

  end loop myLoop;
  close curs; 
END;
COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_sales_amount_for_dashboard
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_sales_amount_for_dashboard`;
DELIMITER ;;
CREATE  PROCEDURE `sp_sales_amount_for_dashboard`(IN `c_date` datetime,IN `cshop_id` int,
IN `c_start_week_date` datetime, IN `c_start_year_date` datetime,c_option int,c_weeknumber int)
BEGIN
 declare n_tamount  FLOAT;
 declare n_wamount  FLOAT;
 declare n_yamount  FLOAT;
 declare n_lastyear_tamount  FLOAT;
 declare n_lastyear_wamount  FLOAT;
 declare n_lastyear_yamount  FLOAT;
 declare n_defined_weekly_target  FLOAT;

 declare out_tamount  FLOAT;
 declare out_wamount  FLOAT;
 declare out_yamount  FLOAT;
 declare out_lastyear_tamount  FLOAT;
 declare out_lastyear_wamount  FLOAT;
 declare out_lastyear_yamount  FLOAT;
 declare out_defined_weekly_target  FLOAT;
 START TRANSACTION;
  BEGIN
   CASE c_option
     
     WHEN 0 THEN
	BEGIN 
		
		SET  n_tamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr 	WHERE 
		 	order_date=c_date);
		SET out_tamount=n_tamount;
		update sales_amount_target set current_day_target_amount=IFNULL(out_tamount,0) where shop_id= cshop_id;
	END; 
     
     ELSE
	BEGIN	
	
	SET  n_defined_weekly_target=(SELECT IFNULL(target_amount,0) as target_amount  FROM `v_shop_target_amount` WHERE 
		shop_id= cshop_id and week_no=c_weeknumber and year=YEAR(c_date));
     	
		
		set @prv_date_of_cdate=DATE_SUB(c_date, INTERVAL 1 day); 
		
		SET  n_tamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr 	WHERE 
		 	order_date=c_date);
		
		SET  n_wamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr 	WHERE 
		    	order_date between c_start_week_date and @prv_date_of_cdate);
		
		SET  n_yamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr 	WHERE 
		    	order_date between c_start_year_date and @prv_date_of_cdate);

    	
		set @lastyear_cdate=DATE_SUB(c_date, INTERVAL 1 year); 
		set @lastyear_prv_date_of_cdate=DATE_SUB(c_date, INTERVAL 1 year); 
		set @lastyear_start_week_date=DATE_SUB(c_start_week_date, INTERVAL 1 year); 
		set @lastyear_start_year_date=DATE_SUB(c_start_year_date, INTERVAL 1 year); 

		
		SET  n_lastyear_tamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr WHERE 
		 	order_date=@lastyear_cdate);
		
		SET  n_lastyear_wamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr WHERE 
		    	order_date between @lastyear_start_week_date and @lastyear_prv_date_of_cdate);
		
		SET  n_lastyear_yamount=(SELECT sum(total_amount) as total_today  FROM `order_hdrs` hdr WHERE 
		    	order_date between @lastyear_start_year_date and @lastyear_prv_date_of_cdate);
	
	
	SET out_defined_weekly_target=n_defined_weekly_target;
	
		SET out_tamount=n_tamount;
		SET out_wamount=n_wamount;
		SET out_yamount=n_yamount;
	
		SET out_lastyear_tamount=n_lastyear_tamount;
		SET out_lastyear_wamount=n_lastyear_wamount;
		SET out_lastyear_yamount=n_lastyear_yamount;

	   BEGIN
		DELETE FROM sales_amount_target;
		insert into sales_amount_target(shop_id,defined_weekly_target,last_calculated_date,current_day_target_amount,weekly_amount,
					monthly_amount,yearly_amount,lastyear_sameday_target_amount,lastyear_weekly_amount,
					lastyear_yearly_amount)
	 		select  cshop_id,IFNULL(out_defined_weekly_target,0),c_date,IFNULL(out_tamount,0) ,IFNULL(out_wamount,0),0,IFNULL(out_yamount,0),
	 		IFNULL(out_lastyear_tamount,0) , IFNULL(out_lastyear_wamount,0),IFNULL(out_lastyear_yamount,0); 
	   END;
	END;
     END CASE;		
   END;
  COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_sales_amount_target_table_update_from_shop
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_sales_amount_target_table_update_from_shop`;
DELIMITER ;;
CREATE PROCEDURE `sp_sales_amount_target_table_update_from_shop`(IN `c_date` datetime,IN `cshop_id` int,
IN `c_start_week_date` datetime, IN `c_start_year_date` datetime,c_option int,c_weeknumber int,
IN nsales_amount FLOAT,IN nsales_target FLOAT,IN nitem_sold FLOAT,IN ntransactions FLOAT,IN nstaff_hours VARCHAR(20),
IN nstaff_hours_target VARCHAR(20),IN nlabour_cost FLOAT,IN nlabour_cost_target FLOAT,
IN ntotal_tax FLOAT,IN  nsales_status  INT,IN ntotal_extras FLOAT,IN ntotal_detail_discount FLOAT,
IN ntotal_bill_discount_amt FLOAT,IN ntotal_refund_amt FLOAT,
IN ntotal_bill_discount_tax FLOAT,IN ntotal_refund_tax FLOAT,IN ntotal_extras_tax FLOAT)
BEGIN
 declare n_tamount  FLOAT;
 declare n_wamount  FLOAT;
 declare n_yamount  FLOAT;
 declare n_lastyear_tamount  FLOAT;
 declare n_lastyear_wamount  FLOAT;
 declare n_lastyear_yamount  FLOAT;
 declare n_defined_weekly_target  FLOAT;

 declare noMoreRows integer;

 declare n_item_sold_today  FLOAT;
 declare n_item_sold_this_week  FLOAT;
 declare n_item_sold_lastyear_this_week  FLOAT;

 declare n_order_trans_today  FLOAT;
 declare n_order_trans_this_week  FLOAT;
 declare n_order_trans_lastyear_this_week  FLOAT;

 declare n_staff_hrs_today  VARCHAR(20);
 declare n_staff_hrs_this_week   VARCHAR(20);
 declare n_staff_hrs_target   VARCHAR(20);
 declare n_staff_hrs_lastyear_this_week   VARCHAR(20);
 declare n_staff_hrs_yearly_amount   VARCHAR(20);
 declare n_staff_hrs_lastyear_yearly_amount   VARCHAR(20);
 declare n_labour_cost_today  FLOAT;
 declare n_labour_cost_this_week  FLOAT;
 declare n_labour_cost_target  FLOAT;
 declare n_labour_cost_lastyear_this_week  FLOAT;
 declare n_labour_cost_yearly_amount  FLOAT;
 declare n_labour_cost_lastyear_yearly_amount  FLOAT;
 declare prv_date_of_cdate DATETIME; 
 declare lastyear_cdate DATETIME; 
 declare lastyear_prv_date_of_cdate DATETIME; 
 declare lastyear_start_week_date DATETIME; 
 declare lastyear_start_year_date DATETIME;

 declare n_total_tax_today  FLOAT;
 declare n_total_tax_weekly  FLOAT;
 declare n_total_tax_yearly  FLOAT;
 declare n_total_tax_last_year_today  FLOAT;
 declare n_total_tax_last_year_weekly  FLOAT;
 declare n_total_tax_last_year  FLOAT;
 declare n_total_expense_today  FLOAT;

 declare n_total_detail_discount  FLOAT;
 declare n_total_bill_discount_amt  FLOAT;
 declare n_total_refund_amt  FLOAT;
 declare n_total_bill_discount_tax  FLOAT;
 declare n_total_refund_tax  FLOAT;
 declare n_total_extras_tax  FLOAT;



 START TRANSACTION;
  BEGIN
	SET n_tamount=nsales_amount;
	SET n_defined_weekly_target=nsales_target;
	SET n_item_sold_today=nitem_sold;
	SET n_order_trans_today=ntransactions;
	SET n_staff_hrs_today=nstaff_hours;
	SET n_staff_hrs_target=nstaff_hours_target;
	SET n_labour_cost_today	=nlabour_cost;
	SET n_labour_cost_target=nlabour_cost_target;
	SET n_total_tax_today=ntotal_tax;
    SET n_total_expense_today=ntotal_extras;

    SET n_total_detail_discount=ntotal_detail_discount;
    SET n_total_bill_discount_amt=ntotal_bill_discount_amt;
    SET n_total_refund_amt=ntotal_refund_amt;
    SET n_total_bill_discount_tax=ntotal_bill_discount_tax;
    SET n_total_refund_tax=ntotal_refund_tax;
    SET n_total_extras_tax=ntotal_extras_tax;


   CASE c_option
     
     WHEN 0 THEN
	BEGIN 
		
		update sales_amount_target set current_day_target_amount=IFNULL(n_tamount,0),
		item_sold_today=IFNULL(n_item_sold_today,0),order_trans_today=IFNULL(n_order_trans_today,0),
		staff_hrs_today=IFNULL(n_staff_hrs_today,'00:00:00'),labour_cost_today=IFNULL(n_labour_cost_today,0),
		total_tax=IFNULL(n_total_tax_today,0),current_day_total_extras=IFNULL(n_total_expense_today,0),
        current_day_total_detail_discount=IFNULL(n_total_detail_discount,0),
        current_day_totalbill_discount_amt=IFNULL(n_total_bill_discount_amt,0),
        current_day_total_refundamt=IFNULL(n_total_refund_amt,0),
        current_day_total_bill_discount_tax=IFNULL(n_total_bill_discount_tax,0),
        current_day_total_refund_tax=IFNULL(n_total_refund_tax,0),current_day_total_extra_charge_tax=IFNULL(n_total_extras_tax,0)
         where sales_date=c_date and shop_id= cshop_id;
	END; 
     ELSE
	BEGIN	
		set prv_date_of_cdate=DATE_SUB(c_date, INTERVAL 1 day); 
		set lastyear_cdate=DATE_SUB(c_date, INTERVAL 1 year); 
		set lastyear_prv_date_of_cdate=DATE_SUB(c_date, INTERVAL 1 year); 
		set lastyear_start_week_date=DATE_SUB(c_start_week_date, INTERVAL 1 year); 
		set lastyear_start_year_date=DATE_SUB(c_start_year_date, INTERVAL 1 year); 
	 
	 BEGIN  
	  declare curs cursor for SELECT sum(sales_amount+total_extras+total_extracharge_tax)-sum(total_refund_amt+total_detail_discount+total_bill_discount) ,sum(item_sold) ,sum(transactions),
					SEC_TO_TIME(SUM(TIME_TO_SEC(staff_hours))),sum(labour_cost),sum(total_tax)+sum(total_extracharge_tax)- sum(refund_tax)-sum(total_bill_discount_tax)  FROM dashboard_sales WHERE  
		    					sales_date between c_start_week_date and prv_date_of_cdate;
	  declare continue handler for not found set noMoreRows = 1;
	  set noMoreRows = 0;
	  open curs;
	  myLoop:loop
	    fetch curs into n_wamount,n_item_sold_this_week,n_order_trans_this_week,n_staff_hrs_this_week,n_labour_cost_this_week,n_total_tax_weekly;
	    if noMoreRows then
	      leave myLoop;
	    end if;
	  end loop myLoop;
	  close curs; 
	 END;
	
	 BEGIN
	  declare curs cursor for SELECT sum(sales_amount+total_extras)-sum(total_refund_amt+total_detail_discount+total_bill_discount),SEC_TO_TIME(SUM(TIME_TO_SEC(staff_hours))),sum(labour_cost),sum(total_tax+total_extracharge_tax)- sum(refund_tax+total_bill_discount_tax)   FROM dashboard_sales WHERE  
		    					sales_date between c_start_year_date and prv_date_of_cdate;
	  declare continue handler for not found set noMoreRows = 1;
	  set noMoreRows = 0;
	  open curs;
	  myLoop:loop
	    fetch curs into n_yamount,n_staff_hrs_yearly_amount,n_labour_cost_yearly_amount,n_total_tax_yearly;
	    if noMoreRows then
	      leave myLoop;
	    end if;
	
	  end loop myLoop;
	  close curs; 
	 END;
	
	 BEGIN
	  declare curs cursor for SELECT sum(sales_amount+total_extras)-sum(total_refund_amt+total_detail_discount+total_bill_discount) as total_today,sum(item_sold) as total_today,sum(transactions) as total_today,
					SEC_TO_TIME(SUM(TIME_TO_SEC(staff_hours))), sum(labour_cost),sum(total_tax+total_extracharge_tax)- sum(refund_tax+total_bill_discount_tax)    FROM dashboard_sales WHERE  
		    					sales_date between  lastyear_start_week_date and lastyear_cdate;
	  declare continue handler for not found set noMoreRows = 1;
	  set noMoreRows = 0;
	  open curs;
	  myLoop:loop
	    fetch curs into  n_lastyear_wamount,n_item_sold_lastyear_this_week, n_order_trans_lastyear_this_week,
				n_staff_hrs_lastyear_this_week,n_labour_cost_lastyear_this_week,n_total_tax_last_year_weekly;
	    if noMoreRows then
	      leave myLoop;
	    end if;
	
	  end loop myLoop;
	  close curs; 
	 END;
	
	 BEGIN
	  declare curs cursor for SELECT sum(sales_amount+total_extras)-sum(total_refund_amt+total_detail_discount+total_bill_discount) as total_today,SEC_TO_TIME(SUM(TIME_TO_SEC(staff_hours))),sum(labour_cost),sum(total_tax+total_extracharge_tax)- sum(refund_tax+total_bill_discount_tax)  
				  FROM dashboard_sales WHERE  sales_date between   lastyear_start_year_date and  lastyear_cdate;
	  declare continue handler for not found set noMoreRows = 1;
	  set noMoreRows = 0;
	  open curs;
	  myLoop:loop
	    fetch curs into   n_lastyear_yamount,n_staff_hrs_lastyear_yearly_amount,n_labour_cost_lastyear_yearly_amount,n_total_tax_last_year;
	    if noMoreRows then
	      leave myLoop;
	    end if;
	
	  end loop myLoop;
	  close curs; 
	 END;

	   BEGIN
		DELETE FROM sales_amount_target where shop_id=cshop_id;
		insert into sales_amount_target(shop_id,defined_weekly_target,last_calculated_date,current_day_target_amount,weekly_amount,
					yearly_amount,lastyear_weekly_amount,lastyear_yearly_amount,
					item_sold_today,item_sold_this_week,item_sold_lastyear_this_week,order_trans_today,order_trans_this_week,
					order_trans_lastyear_this_week,staff_hrs_today,staff_hrs_this_week,staff_hrs_target,staff_hrs_lastyear_this_week,
					staff_hrs_yearly,staff_hrs_lastyear_yearly,labour_cost_today,labour_cost_this_week,labour_cost_target,
					labour_cost_lastyear_this_week,labour_cost_yearly_amount,labour_cost_lastyear_yearly_amount,
					current_day_total_tax,weekly_total_tax,yearly_total_tax,lastyear_weekly_total_tax,lastyear_yearly_total_tax,current_day_total_extras,current_day_total_detail_discount,
					current_day_totalbill_discount_amt,current_day_total_refundamt,current_day_total_bill_discount_tax,current_day_total_refund_tax,current_day_total_extra_charge_tax)
	 		select  cshop_id,IFNULL(n_defined_weekly_target,0),c_date,IFNULL(n_tamount,0) ,IFNULL(n_wamount,0),
				IFNULL(n_yamount,0),IFNULL(n_lastyear_wamount,0),IFNULL(n_lastyear_yamount,0),
			IFNULL(n_item_sold_today,0),IFNULL(n_item_sold_this_week,0),IFNULL(n_item_sold_lastyear_this_week,0),
			IFNULL(n_order_trans_today,0),IFNULL(n_order_trans_this_week,0),IFNULL(n_order_trans_lastyear_this_week,0),
			IFNULL(n_staff_hrs_today,'00:00:00'),IFNULL(n_staff_hrs_this_week,'00:00:00'),IFNULL(n_staff_hrs_target,'00:00:00'),
			IFNULL(n_staff_hrs_lastyear_this_week,'00:00:00'),IFNULL(n_staff_hrs_yearly_amount,'00:00:00'),
			IFNULL(n_staff_hrs_lastyear_yearly_amount,'00:00:00'),IFNULL(n_labour_cost_today,0),IFNULL(n_labour_cost_this_week,0),
			IFNULL(n_labour_cost_target,0),	IFNULL(n_labour_cost_lastyear_this_week,0),
			IFNULL(n_labour_cost_yearly_amount,0),IFNULL(n_labour_cost_lastyear_yearly_amount,0),
			IFNULL(n_total_tax_today,0),IFNULL(n_total_tax_weekly,0),IFNULL(n_total_tax_yearly,0),
			IFNULL(n_total_tax_last_year_weekly,0),IFNULL(n_total_tax_last_year,0),
            IFNULL(n_total_expense_today,0),IFNULL(n_total_detail_discount,0),IFNULL(n_total_bill_discount_amt,0),
            IFNULL(n_total_refund_amt,0),IFNULL(n_total_bill_discount_tax,0),IFNULL(n_total_refund_tax,0),IFNULL(n_total_extras_tax,0); 
	   END;
	END;
     END CASE;		
   END;
  COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_sales_summary_schedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_sales_summary_schedule`;
DELIMITER ;;
CREATE  PROCEDURE `sp_sales_summary_schedule`(IN `year_start` datetime,IN `c_date` datetime,IN `n_shop_id` int,
c_shop_name varchar(50),`n_area_codes_id` int, `c_area_codes_name` varchar(50),n_weeknumber int,curr_date varchar(15),star_week_date varchar(15))
BEGIN
 declare n_department_id  BIGINT;
 declare c_department_name  VARCHAR(50);
 declare n_super_class_id BIGINT;
 declare c_super_class_name  VARCHAR(50);
 declare n_sub_class_id  BIGINT;
 declare c_sub_class_name  VARCHAR(50);
 declare n_sale_item_id   BIGINT;
 declare c_sale_item_code  VARCHAR(10);
 declare c_sale_item_name  VARCHAR(50);
 declare n_fixed_price   FLOAT;
 declare n_qty  FLOAT;
 declare c_uom_name  VARCHAR(50);
 declare c_uom_symbol  VARCHAR(1);
 declare n_item_total  FLOAT;
 declare n_tax_amount   FLOAT;
 declare n_discount_amount  FLOAT;
 declare n_item_cost  FLOAT;
 declare nCounter integer;
 declare noMoreRows integer;
 declare nStatus integer;
 declare nWeekCounter integer;
 START TRANSACTION;

  BEGIN
	SET nWeekCounter=7;
	WHILE c_date <= curr_date DO
      		
	
	if c_date < curr_date then
      			SET nStatus=1;
	ELSE 
			SET nStatus=0;
    	end if;	
	
	if c_date= DATE_ADD(star_week_date, INTERVAL nWeekCounter DAY) then
		BEGIN
      			SET n_weeknumber=n_weeknumber+1;
			SET nWeekCounter=nWeekCounter+7;
		END;
    	end if;	
	
BEGIN	
   declare curs cursor for SELECT sub.department_id,sub.department_name,sub.super_class_id,sub.super_class_name,dtl.sub_class_id,
	sub_class_name,sale_item_id,sale_item_code,dtl.name,dtl.fixed_price,sum(qty),uom_name,uom_symbol,sum(item_total),
	sum(tax1_amount+tax2_amount+tax3_amount+gst_amount+sc_amount) as tax_amount,sum(discount_amount) as discount_amount,
   	sl.item_cost from order_dtls dtl inner join (SELECT dep.name as department_name,sp.`id` as super_class_id,sp.name as super_class_name,
	sub.id as sub_class_id,sp.`department_id` FROM `item_classes` sub inner join `item_classes` sp on sp.id=sub.super_class_id 
	and sp.department_id=sub.department_id and sp.super_class_id is null and sub.super_class_id is not null 
	inner join departments dep on dep.id=sp.department_id and dep.id=sub.department_id) sub on sub.sub_class_id=dtl.sub_class_id
	left join sale_items as sl on sl.id=dtl.sale_item_id
	WHERE dtl.order_date=c_date and dtl.is_void !=1 group by sale_item_id, dtl.order_date ;
   declare continue handler for not found set noMoreRows = 1;
      set noMoreRows = 0;
      open curs;
      myLoop:loop
         fetch curs into n_department_id,c_department_name,n_super_class_id,c_super_class_name,n_sub_class_id,c_sub_class_name,n_sale_item_id,
		c_sale_item_code,c_sale_item_name,n_fixed_price,n_qty,c_uom_name,c_uom_symbol,n_item_total,n_tax_amount,n_discount_amount,n_item_cost;
            if noMoreRows then
               leave myLoop;
            end if;
	
	SET nCounter=(SELECT count(id) as sales_id from  sales_summary_table where summary_date=c_date AND sale_item_id=n_sale_item_id);
	
	BEGIN

	IF (nCounter=0) THEN	
 	   INSERT INTO `sales_summary_table` ( `week_no`, `summary_date`, `shop_id`, `shop_name`, `area_codes_id`, 
		`area_codes_name`, `department_id`, `department_name`, `report_department_id`, `report_department_name`, 
		`super_class_id`, `super_class_name`, `sub_class_id`, `sub_class_name`, `sale_item_id`, `sale_item_code`,
		 `sale_item_name`,fixed_price, `qty`, `uom_id`, `uom_name`, `uom_symbol`, `total_amount`, `total_tax`, `total_discount`,`status`,`item_cost`) 
	 		select n_weeknumber,IFNULL(c_date,0),IFNULL(n_shop_id,0), IFNULL(c_shop_name,0),
			IFNULL(n_area_codes_id,0),IFNULL(c_area_codes_name,''),IFNULL(n_department_id,0),
			IFNULL(c_department_name,''),0,'',
			n_super_class_id,IFNULL(c_super_class_name,''),IFNULL(n_sub_class_id,0),IFNULL(c_sub_class_name,''),
			IFNULL(n_sale_item_id,0),IFNULL(c_sale_item_code,''),IFNULL(c_sale_item_name,0),
			IFNULL(n_fixed_price,0),IFNULL(n_qty,0),0,IFNULL(c_uom_name,''),
			IFNULL(c_uom_symbol,''),IFNULL(n_item_total,0),IFNULL(n_tax_amount,0),IFNULL(n_discount_amount,0),nStatus,IFNULL(n_item_cost,0);
	ELSE
		UPDATE `sales_summary_table` set `qty`=IFNULL(n_qty,0),total_amount=IFNULL(n_item_total,0),
			total_tax=IFNULL(n_tax_amount,0),total_discount=IFNULL(n_discount_amount,0),status=nStatus,item_cost=IFNULL(n_item_cost,0)
			where summary_date=c_date AND sale_item_id=n_sale_item_id;
	END IF;	
	END;
     end loop myLoop;
      close curs; 
 END;

      		Set c_date = DATE_ADD(c_date,INTERVAL 1 DAY);
    	END WHILE;
  END;

  COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_shop_attendance_auto_update_shift
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_shop_attendance_auto_update_shift`;
DELIMITER ;;
CREATE  PROCEDURE `sp_shop_attendance_auto_update_shift`()
BEGIN
 declare n_id  BIGINT(20);
 declare n_employee_id  BIGINT(20);
 declare c_shift_start_date  DATE;
 declare c_shift_start_time  TIME;
 declare c_shift_end_date  DATE;
 declare c_shift_end_time  TIME;
 declare noMoreRows integer;
START TRANSACTION;
BEGIN
	
  	declare curs cursor for SELECT  id,employee_id,shift_start_date,shift_start_time,shift_end_date,shift_end_time 
		from txn_staff_attendance WHERE is_processed =0  and is_deleted=0;
 	declare continue handler for not found set noMoreRows = 1;
  	set noMoreRows = 0;
  	open curs;
 	  myLoop:loop
    		fetch curs into n_id,n_employee_id,c_shift_start_date,c_shift_start_time,c_shift_end_date,c_shift_end_time;
    		if noMoreRows then
      			leave myLoop;
    		end if;
		
		SET @shift_id=(select IFNULL(shift_id,0) from v_staff_dutyroaster_details where  employee_id=n_employee_id AND 
		( CONCAT(c_shift_start_date,' ',c_shift_start_time) BETWEEN start_date_from AND start_date_to 
		 AND CONCAT(c_shift_end_date,' ',c_shift_end_time) BETWEEN end_date_from AND end_date_to) );
		IF (@shift_id>0)THEN 
			update txn_staff_attendance set shift_id=@shift_id,is_processed=1,status=1 WHERE id=n_id;
		 ELSE
		   BEGIN
			SET @shift_id=(select IFNULL(sh.id,0) from shop_shifts sh where 1=1 
				AND (c_shift_start_time BETWEEN SUBTIME(sh.start_time, allowed_time_before_start )AND 
					ADDTIME( sh.start_time ,allowed_time_after_start)
				AND c_shift_end_time  BETWEEN SUBTIME(sh.end_time,allowed_time_before_end )AND 
					ADDTIME( sh.end_time ,allowed_time_after_end)));
			IF (@shift_id>0)THEN 
				update txn_staff_attendance set shift_id=@shift_id,is_processed=1,status=1 WHERE id=n_id;
			ELSE
				update txn_staff_attendance set is_processed=1,status=0 WHERE id=n_id;
			END IF ;
		   END;
		 
		END IF ;
	 end loop myLoop;
  	close curs;
END;
COMMIT;	
END
;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for sp_staff_cost_summary_schedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_staff_cost_summary_schedule`;
DELIMITER ;;
CREATE  PROCEDURE `sp_staff_cost_summary_schedule`(IN `year_start` datetime,IN `c_date` datetime,IN `n_shop_id` int,
c_shop_name varchar(50),`n_area_codes_id` int, `c_area_codes_name` varchar(50),n_weeknumber int,curr_date varchar(15),star_week_date varchar(15))
BEGIN
 declare n_department_id  BIGINT;
 declare c_department_name  VARCHAR(50);
 declare n_emp_id BIGINT;
 declare c_emp_code  VARCHAR(10);
 declare c_emp_name  VARCHAR(50);
 declare n_emp_category_id  BIGINT;
 declare c_emp_category_name  VARCHAR(50);
 declare n_over_time_pay_rate  FLOAT;
 declare n_cost_per_hour  FLOAT;
 declare n_shift_id  BIGINT;
 declare c_shift_name  VARCHAR(50);
 declare c_shifttotal_hours   VARCHAR(15);
 declare c_iterval_time  VARCHAR(15);
 declare n_iterval_is_payable  tinyint;
 declare n_overtime_payable  tinyint;
 declare c_total_work_hours  VARCHAR(15);
 declare n_unscheduled_breaks  FLOAT;
 declare c_total_work_hours_minutes  VARCHAR(15);
 declare c_overtime_work_hours  VARCHAR(15);
 declare n_overtime_cost  FLOAT;
 declare n_normal_time_cost  FLOAT;
 declare nCounter integer;
 declare noMoreRows integer;
 declare nStatus integer;
 declare nWeekCounter integer;
 START TRANSACTION;

  BEGIN
	SET nWeekCounter=7;
	
	DELETE FROM `staff_cost_summary` WHERE summary_date between c_date AND curr_date;
	WHILE c_date <= curr_date DO
      		
	
	if c_date < curr_date then
      			SET nStatus=1;
	ELSE 
			SET nStatus=0;
    	end if;	
	
	if c_date= DATE_ADD(star_week_date, INTERVAL nWeekCounter DAY) then
		BEGIN
      			SET n_weeknumber=n_weeknumber+1;
			SET nWeekCounter=nWeekCounter+7;
		END;
    	end if;	
	
BEGIN	
   declare curs cursor for select em.*,at.shift_id,at.shift_name,at.total_hours,at.interval_time,at.interval_is_payable,at.work_hours,
	at.unscheduled_breaks,at.overtime_is_payable    from (SELECT emp.id as emp_id,emp.code as emp_code,concat(emp.f_name,' ',emp.m_name,' ',emp.l_name) as emp_name,
	emc.id as emp_category_id,emc.name as emp_category,emp.over_time_pay_rate,emp.cost_per_hour,
	dep.id as dep_id,dep.name as dep_name from employees emp inner join employee_categories emc
	on emp.employee_category_id	=emc.id and emp.is_deleted=0 and emc.is_deleted=0 inner join departments dep on emp.department_id=dep.id and dep.is_deleted=0) em
	inner join (	select txn.id,txn.employee_id,txn.shift_id,sh.name as shift_name,sh.total_hours,
 	TIME_TO_SEC( CASE WHEN TIMEDIFF(concat(txn.shift_start_date,' ',interval_end_time),
	concat(txn.shift_start_date,' ',interval_start_time)) <0 THEN	'0' else TIMEDIFF(concat(txn.shift_start_date,' ',interval_end_time),
	concat(txn.shift_start_date,' ',interval_start_time)) end)  as interval_time,interval_is_payable,
	TIME_TO_SEC( CASE WHEN TIMEDIFF(concat(txn.shift_end_date,' ', txn.shift_end_time),
	concat(txn.shift_start_date,' ', txn.shift_start_time)) <0 THEN	'0' else TIMEDIFF(concat(txn.shift_end_date,' ', txn.shift_end_time),
	concat(txn.shift_start_date,' ', txn.shift_start_time)) end) as work_hours, IFNULL(TIME_TO_SEC( CASE WHEN TIMEDIFF(unscheduled_breakout,
	unscheduled_breakin) <0 THEN	'0' else TIMEDIFF(unscheduled_breakout,	unscheduled_breakin) end),0) as unscheduled_breaks,sh.overtime_is_payable	
	from txn_staff_attendance txn inner join  shop_shifts sh on sh.id=txn.shift_id and txn.is_processed = 1 and txn.status = 1 and txn.is_deleted=0 
	and	sh.is_deleted=0 AND  shift_start_date=c_date)   at on em.emp_id=at.employee_id ;
   declare continue handler for not found set noMoreRows = 1;
      set noMoreRows = 0;
      open curs;
      myLoop:loop
         fetch curs into  n_emp_id,c_emp_code,c_emp_name,n_emp_category_id,c_emp_category_name,n_over_time_pay_rate,n_cost_per_hour,n_department_id,
			c_department_name,n_shift_id,c_shift_name,c_shifttotal_hours,c_iterval_time,n_iterval_is_payable,
			c_total_work_hours,n_unscheduled_breaks,n_overtime_payable  ;
            if noMoreRows then
               leave myLoop;
            end if;
	
	
	
	BEGIN
	 
	
	IF n_overtime_payable=1 THEN
		SET c_overtime_work_hours=IFNULL(TIMEDIFF(TIME_TO_SEC(c_shifttotal_hours), c_total_work_hours),0);
		SET c_total_work_hours=c_total_work_hours-c_overtime_work_hours;
		SET n_overtime_cost=round(n_over_time_pay_rate*c_overtime_work_hours/3600,2);
	END IF;
	
	IF n_unscheduled_breaks !=0 THEN
		SET c_total_work_hours=c_total_work_hours-n_unscheduled_breaks;
	END IF; 
	
	IF  n_iterval_is_payable=0 THEN 
		SET c_total_work_hours=c_total_work_hours-c_iterval_time;
	END IF ;

	SET n_normal_time_cost=round(n_cost_per_hour*c_total_work_hours/3600,2);

	END;
	
 	INSERT INTO `staff_cost_summary` ( `week_no`, `summary_date`, shop_id, shop_name,`area_codes_id`, 
		`area_codes_name`, `department_id`, `department_name`, `report_department_id`, `report_department_name`, 
		`emp_category_id`, `emp_category_name`, `emp_id`, `emp_code`, `emp_name`, `cost_per_hour`,
		 `overtime_pay_rate`,`shift_id`, `shift_name`, `shift_total_hours`, `normal_work_hours`, `normal_cost`, `overtime_work_hours`, `overtime_cost`,
			 `interval_hours`,`unscheduled_break_hours`,`status`) 
			select n_weeknumber,c_date,n_shop_id, c_shop_name,
			n_area_codes_id,c_area_codes_name,n_department_id,
			c_department_name,0,'',n_emp_category_id,c_emp_category_name,n_emp_id,c_emp_code,c_emp_name,n_cost_per_hour,n_over_time_pay_rate,n_shift_id,c_shift_name,c_shifttotal_hours,
			IFNULL(c_total_work_hours,0),	IFNULL(n_normal_time_cost,0),IFNULL(c_overtime_work_hours,0),IFNULL(n_overtime_cost,0),
			IFNULL(c_iterval_time,0),IFNULL(n_unscheduled_breaks,0),nStatus;


     end loop myLoop;
      close curs; 
 END;

      		Set c_date = DATE_ADD(c_date,INTERVAL 1 DAY);
    	END WHILE;
  END;

  COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_stock_adjustment_insert
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_stock_adjustment_insert`;
DELIMITER ;;
CREATE  PROCEDURE `sp_stock_adjustment_insert`(IN `n_stock_adjustment_hdr_id` BIGINT, IN `n_department_id` BIGINT(20), IN `n_storage_id` BIGINT(20), IN `n_stock_item_id` BIGINT(20), IN `c_stock_item_locations` VARCHAR(50), IN `n_uom_id` BIGINT(20), IN `n_system_qty` FLOAT(8,2), IN `n_actual_qty` FLOAT(8,2), IN `n_diff_qty` FLOAT(8,2), IN `c_reason` VARCHAR(255), IN `n_last_purchase_unit_price` FLOAT(8,2), IN `n_converted_qty` FLOAT(8,2))
BEGIN
declare n_storage_qty FLOAT;
declare n_last_po_price FLOAT;
	SELECT qty ,last_purchase_unit_price INTO n_storage_qty,n_last_po_price FROM `stock_item_storages` sts  
		INNER JOIN stock_items st on st.id=sts.stock_item_id WHERE sts.stock_item_id=n_stock_item_id 
		and sts.department_id=n_department_id and sts.storage_id=n_storage_id AND st.id=stock_item_id LIMIT 0,1;
   	IF (n_storage_qty!=n_system_qty) THEN
     	    SET n_storage_qty=n_storage_qty+n_diff_qty;
   	END IF;
	INSERT INTO  txn_stock_adjustment_dtls(stock_adjustment_hdr_id,stock_item_id,department_id,storage_id,stock_item_locations,
		system_qty,actual_qty,diff_qty,uom_id,converted_qty,reason,last_purchase_unit_price)
	VALUES (n_stock_adjustment_hdr_id,n_stock_item_id,n_department_id,n_storage_id,c_stock_item_locations,
		n_system_qty,n_actual_qty,n_diff_qty,n_uom_id,n_converted_qty,c_reason,n_last_po_price);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_stock_adjustment_on_approve
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_stock_adjustment_on_approve`;
DELIMITER ;;
CREATE  PROCEDURE `sp_stock_adjustment_on_approve`(IN n_stock_adjustment_hdr_id BIGINT,
IN is_update_stock_in_hand TINYINT)
BEGIN
declare n_stock_item_id BIGINT(20);
declare n_department_id BIGINT(20);
declare n_storage_id BIGINT(20);
declare n_storage_qty FLOAT;
declare n_last_po_price FLOAT;
declare n_system_qty FLOAT;
declare n_actual_qty FLOAT;
declare n_diff_qty FLOAT;
declare n_qty FLOAT;
declare noMoreRows integer;
START TRANSACTION;
 	BEGIN
  	declare curs cursor for SELECT dtl.stock_item_id,dtl.department_id,dtl.storage_id,dtl.system_qty,dtl.actual_qty,
		dtl.diff_qty,sts.qty FROM `txn_stock_adjustment_dtls` dtl 
			INNER JOIN stock_item_storages sts on
			dtl.stock_item_id=sts.stock_item_id and  dtl.department_id=sts.department_id
			AND dtl.storage_id=sts.storage_id WHERE  stock_adjustment_hdr_id=n_stock_adjustment_hdr_id;
 	declare continue handler for not found set noMoreRows = 1;
  	set noMoreRows = 0;
  	open curs;
 	  myLoop:loop
    		fetch curs into n_stock_item_id,n_department_id,n_storage_id,n_system_qty,n_actual_qty,n_diff_qty,n_qty;
    		if noMoreRows then
      			leave myLoop;
    		end if;
		update stock_item_storages set qty=(n_actual_qty-n_system_qty)+n_qty where 
			stock_item_id=n_stock_item_id and  department_id=n_department_id AND storage_id=n_storage_id;
		IF(is_update_stock_in_hand=1) THEN
			SELECT sum(qty) INTO @qty_in_hand FROM `stock_item_storages`  WHERE  stock_item_id=n_stock_item_id 
			and  department_id=n_department_id  group by department_id;
	   		update stock_items set qty_on_hand=@qty_in_hand  where `id`=n_stock_item_id;
		END IF;
	  end loop myLoop;
  	close curs; 
 	END;
 COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_temp_sync_que_for_combo_item_delete
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_temp_sync_que_for_combo_item_delete`;
DELIMITER ;;
CREATE  PROCEDURE `sp_temp_sync_que_for_combo_item_delete`(IN `nshop_id` VARCHAR(255),IN `tbl_name` VARCHAR(255),IN `rec_id` int,IN `crud_action` VARCHAR(1))
BEGIN
 declare n_id2  BIGINT(20);
 declare n_id3  BIGINT(20);
 declare noMoreRows2 integer;
 declare noMoreRows3 integer;
IF(nshop_id=0) then 
	set @c_origion='HQ';
else
	set @c_origion='SHOP';
END IF;
 START TRANSACTION;
 BEGIN

  delete from  temp_sync_queue ;

BEGIN
	
  declare combo_curs cursor for SELECT id FROM combo_contents where combo_sale_item_id=rec_id  and is_deleted=0;
  declare continue handler for not found set noMoreRows2 = 1;
  set noMoreRows2 = 0;
  open combo_curs;
  myLoop2:loop
    fetch combo_curs into n_id2;
    if noMoreRows2 then
      leave myLoop2;
    end if;
	
	INSERT INTO `temp_sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`)
 		VALUES ('combo_contents', n_id2, nshop_id, crud_action);	
	
	
	BEGIN	
		declare substitution_curs cursor for SELECT id  FROM combo_content_substitutions where combo_content_id=n_id2 and is_deleted=0;
		Declare continue handler for not found set noMoreRows3 = 1;
  		set noMoreRows3 = 0;
		open substitution_curs;	
  		myLoop3:loop
   	 		fetch substitution_curs into n_id3;
   	 		if noMoreRows3 then
      				leave myLoop3;
    			end if;
			INSERT INTO `temp_sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`)
 				VALUES ('combo_content_substitutions', n_id3, nshop_id, crud_action);
	
		end loop myLoop3;
		close substitution_curs; 
	END; 
	
	set @table_name = 'combo_content_substitutions';
	SET @sql_text = concat('UPDATE ',@table_name,' set is_deleted=1  WHERE combo_content_id=', n_id2);
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt; 
	
  end loop myLoop2;
  close combo_curs; 
END;

	set @table_name = 'combo_contents';
	SET @sql_text = concat('UPDATE ',@table_name,' set is_deleted=1  WHERE combo_sale_item_id=', rec_id);
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt; 

	set @table_name = 'sale_items';
	SET @sql_text = concat('UPDATE ',@table_name,' set  is_combo_item =0  WHERE id=', rec_id);
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt; 
	
	INSERT INTO `temp_sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`)
 		VALUES ('sale_items', rec_id, nshop_id, 'U');	 
END;
COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_temp_sync_que_for_sale_item_update_from_item_class
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_temp_sync_que_for_sale_item_update_from_item_class`;
DELIMITER ;;
CREATE PROCEDURE `sp_temp_sync_que_for_sale_item_update_from_item_class`(
	IN `nshop_id` VARCHAR(255),
	IN `set_sp` TEXT,
	IN `rec_id` int,
	IN `crud_action` VARCHAR(1)
)
BEGIN
 declare n_id2  BIGINT(20);
 declare noMoreRows2 integer;
 START TRANSACTION;
  BEGIN
  
  delete from  temp_sync_queue ;
	
	BEGIN
	  declare sale_item_curs cursor for SELECT id FROM sale_items where sub_class_id=rec_id  and is_deleted=0 and is_synchable=1;
	  declare continue handler for not found set noMoreRows2 = 1;
	  set noMoreRows2 = 0;
	  open sale_item_curs;
	  myLoop2:loop
		fetch sale_item_curs into n_id2;
		if noMoreRows2 then
		  leave myLoop2;
		end if;
		
		INSERT INTO `temp_sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`)
	 		VALUES ('sale_items', n_id2, nshop_id, crud_action);	

		set @table_name = 'sale_items';
		SET @sql_text = concat('UPDATE ',@table_name,' set ',set_sp,' WHERE id=', n_id2);
		PREPARE stmt FROM @sql_text;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt; 
	  end loop myLoop2;
	  close sale_item_curs; 
	END;
	INSERT INTO `temp_sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`)
		VALUES ('item_classes', rec_id, nshop_id, crud_action);	
  END;
 COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_voucher_type_sync
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_voucher_type_sync`;
DELIMITER ;;
CREATE  PROCEDURE `sp_voucher_type_sync`(IN `nvoucher_type_id` int,IN `shop_id` int,
 IN `crud_action` VARCHAR(1),IN `publish_date` datetime)
BEGIN
 declare n_id  BIGINT(20);
 START TRANSACTION;
 BEGIN
	
  	delete from  sync_queue  where  record_id =nvoucher_type_id and table_name='voucher_types' ;
	INSERT INTO `sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`, `publishing_date`, `last_updated`, `sync_status`, `error_msg`)
			 VALUES	(tbl_name, rec_id, shop_id, crud_action, topublish, now(), 0,'');
SET  n_id= (SELECT voucher_type FROM voucher_types where id=nvoucher_type_id and is_deleted=0);
delete from  sync_queue  where  record_id =n_id and table_name='voucher_class' ;
	INSERT INTO `sync_queue` (`table_name`, `record_id`, `shop_id`, `crud_action`, `publishing_date`, `last_updated`, `sync_status`, `error_msg`)
 		VALUES ('voucher_class', n_id, shop_id, crud_action, publish_date, now(), 0,'');	
	set @table_name = 'voucher_types';
	set @table_id ='id';
	SET @sql_text = concat('UPDATE ',@table_name,' set publish_status=1  WHERE ',@table_id,'=', n_id);
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt; 
	 
	 

END;
COMMIT;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getStagingDataCustomer
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStagingDataCustomer`;
DELIMITER ;;
CREATE  PROCEDURE `getStagingDataCustomer`(IN whereCnd  text)
BEGIN
SET @sql=CONCAT('SELECT
customers.`code`,
customers.`name`,
customers.phone AS tel,
"" AS mob,
customers.address AS address1,
CONCAT(
customers.street,IF(LENGTH(customers.city)>0,",",""),
customers.city,IF(LENGTH(customers.state)>0,",",""),
customers.state,IF(LENGTH(customers.country)>0,",",""),
customers.country) AS address2,
customers.is_valid as active,
date(customers.updated_at) as updated_date,
TIME(customers.updated_at) as updated_time
FROM
order_hdrs
INNER JOIN customers ON order_hdrs.customer_id = customers.id
WHERE closing_date IS NOT NULL AND customers.code <> "WALKIN"',
IF(LENGTH(whereCnd)>0, CONCAT(' AND ',whereCnd),''),
';');
-- SELECT @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getStagingDataInvDtl
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStagingDataInvDtl`;
DELIMITER ;;
CREATE  PROCEDURE `getStagingDataInvDtl`(IN param_where_qry text)
BEGIN
SET @rowNo=1;
SET @invoiceNo=0;
SET @sql=CONCAT('SELECT   
	@rowNo:=if(@invoiceNo=invoice_no,@rowNo+1,1) AS sno,
	@invoiceNo:=invoice_no as invoice_no,
	qry.shop_code,
	qry.sale_item_code,
	qry.quantity,
	qry.item_price,
	qry.tax_code,
	qry.item_discount
	FROM (
		SELECT 
			order_hdrs.invoice_no AS invoice_no,
			order_hdrs.order_no,
			order_hdrs.shop_code AS shop_code,
			order_dtls.sale_item_code AS sale_item_code,
			order_dtls.qty - IFNULL((SELECT sum(qty) FROM order_refunds WHERE order_refunds.order_dtl_id= order_dtls.id),0) AS quantity,
			order_dtls.fixed_price AS item_price,
			order_dtls.tax_code,
			order_dtls.discount_amount/order_dtls.qty AS item_discount
		FROM
			order_hdrs
		INNER JOIN order_dtls ON order_hdrs.order_id = order_dtls.order_id
		WHERE order_hdrs.closing_date IS NOT NULL AND order_dtls.is_void=0',
		IF(LENGTH(param_where_qry )>0, CONCAT(' AND ',param_where_qry ),''),
' ORDER BY order_hdrs.invoice_no ) qry',
' WHERE quantity >0'); 
PREPARE stmt FROM @sql;
EXECUTE stmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getStagingDataInvHdr
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStagingDataInvHdr`;
DELIMITER ;;
CREATE  PROCEDURE `getStagingDataInvHdr`(IN whereCnd  text)
BEGIN
DECLARE rowNo INT;
SET rowNo=0;
SET @grossAmount=0;
SET @sql=CONCAT('SELECT 
shop_code,
cust_code,
invoice_date invoice_date,
invoice_no, 
extra_charges,
(total_detail_discount + ((detail_total-detail_tax-total_detail_discount)+extra_charges) * bill_discount_percentage/100)  AS total_discount,
gross_amount-(detail_tax-detail_tax*bill_discount_percentage/100)  - (extra_tax_amount-extra_tax_amount*bill_discount_percentage/100) -(refund_amount - refunded_tax_amount)  AS net_amount,
(detail_tax-detail_tax*bill_discount_percentage/100) + (extra_tax_amount-extra_tax_amount*bill_discount_percentage/100)  - refunded_tax_amount AS total_tax_amount, 
final_round_amount as round_off,
@grossAmount:=gross_amount + final_round_amount - refund_amount as gross_amount,
if(refund_amount>0,
	CASE 
		WHEN @grossAmount-refund_amount<=0 THEN 1
  	ELSE 6
END,
0) as cancelled,
updated_date,
updated_time
FROM
(SELECT
	order_hdrs.shop_code,
	order_hdrs.closing_date AS invoice_date,
	order_hdrs.invoice_no AS invoice_no,
	order_hdrs.detail_total,
	(order_hdrs.total_tax1 + order_hdrs.total_tax2 + order_hdrs.total_tax3 + order_hdrs.total_gst + order_hdrs.total_sc ) AS detail_tax,
	order_hdrs.extra_charges  AS extra_charges,
	(IFNULL(order_hdrs.extra_charge_gst_amount,0) +IFNULL(order_hdrs.extra_charge_tax1_amount,0)+IFNULL(order_hdrs.extra_charge_tax2_amount,0)+IFNULL(order_hdrs.extra_charge_tax3_amount,0))  as extra_tax_amount,
	order_hdrs.total_detail_discount - (SELECT IFNULL(sum(discount_amount),0) from order_dtls INNER JOIN  order_refunds ON order_dtls.id=order_refunds.order_dtl_id WHERE order_dtls.order_id=order_hdrs.order_id ) as total_detail_discount,
	order_hdrs.bill_discount_amount,
	order_hdrs.bill_discount_percentage,
	order_hdrs.final_round_amount,
	order_hdrs.total_amount,
	(order_hdrs.total_amount + (IFNULL(order_hdrs.extra_charges,0)+IFNULL(order_hdrs.extra_charge_gst_amount,0) +IFNULL(order_hdrs.extra_charge_tax1_amount,0)+IFNULL(order_hdrs.extra_charge_tax2_amount,0)+IFNULL(order_hdrs.extra_charge_tax3_amount,0)))-order_hdrs.bill_discount_amount AS gross_amount,
	(order_hdrs.refund_total_tax1+order_hdrs.refund_total_tax2+order_hdrs.refund_total_tax3+order_hdrs.refund_total_sc+order_hdrs.refund_total_sc) AS refunded_tax_amount,
	order_hdrs.refund_amount AS refund_amount,
IF(customers.`code`="WALKIN","CASH",customers.`code`) AS cust_code,
date(order_hdrs.updated_at) AS updated_date,
TIME(order_hdrs.updated_at) AS updated_time
FROM
order_hdrs
INNER JOIN customers ON order_hdrs.customer_id = customers.id
WHERE closing_date IS NOT NULL',
IF(LENGTH(whereCnd)>0, CONCAT(' AND ',whereCnd),''),
' )as qry;');
-- SELECT @sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getStagingDataInvPayments
-- ----------------------------
DROP PROCEDURE IF EXISTS `getStagingDataInvPayments`;
DELIMITER ;;
CREATE  PROCEDURE `getStagingDataInvPayments`(IN whereCnd  text)
BEGIN

SET @rowNo=1;
SET @orderID=0;
SET @sql=CONCAT('SELECT  
shop_code, 
invoice_no,  
@rowNo:=if(@orderID=order_no,@rowNo+1,1) AS sno,
@orderID:=order_no as order_no,
CASE 
	WHEN payment_mode=0 THEN 0
	WHEN payment_mode=1 THEN 2
	WHEN payment_mode=3 THEN 13
END as payment_mode,
SUM(paid_amount)AS amount
 FROM
(SELECT
order_hdrs.shop_code,
order_hdrs.invoice_no,
order_hdrs.order_no as order_no,
IF(order_payments.payment_mode=5,0,order_payments.payment_mode) AS payment_mode,
order_payments.paid_amount*IF(order_payments.payment_mode=5 OR  order_payments.is_repayment=1,-1,1) AS paid_amount,
order_payments.is_repayment as is_refund
FROM
order_payments
INNER JOIN order_hdrs ON order_hdrs.order_id = order_payments.order_id ',
' WHERE order_hdrs.closing_date IS NOT NULL ',
IF(LENGTH(whereCnd)>0, CONCAT(' AND ',whereCnd),''),
' ) as qry
GROUP BY qry.invoice_no, qry.payment_mode
ORDER BY qry.invoice_no;');
PREPARE stmt FROM @sql;
EXECUTE stmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for test_insert
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_insert`;
DELIMITER ;;
CREATE  PROCEDURE `test_insert`(IN `c_id` int)
BEGIN
	IF EXISTS (SELECT id FROM stock_items WHERE id =52) THEN 
		SELECT 'code', code FROM   stock_items  WHERE  id =  5 ;
	else
		SELECT 'name', name FROM  stock_items WHERE  id =  5 ; 
	END IF;
END
;;
DELIMITER ;



-- ----------------------------
-- Procedure structure for test_insert
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_tax_summary`;
DELIMITER ;;

CREATE PROCEDURE `sp_tax_summary`(IN `pos_date` date,
	IN `shiftIds` VARCHAR(50),
	IN terminalIds VARCHAR(50),
  IN userIds VARCHAR(50))
BEGIN

SET @shift_where ="";
SET @ref_shift_where ="";
SET @terminal_where="";
SET @user_where="";
SET @ref_user_where="";
 IF (shiftIds IS NOT NULL AND TRIM(shiftIds)<>'') THEN
		SET @shift_where =CONCAT( " AND  shift_id IN (",shiftIds,") ");
	  SET @ref_shift_where =CONCAT( " AND  opay.cashier_shift_id IN (",shiftIds,") ");
 END IF;
 IF (terminalIds IS NOT NULL AND TRIM(terminalIds)<>'') THEN
		SET @terminal_where =CONCAT(" AND ohdr.station_code IN (SELECT code FROM stations WHERE id IN (",terminalIds,")) ");
 END IF;
 IF (userIds IS NOT NULL AND TRIM(userIds)<>'') THEN
		SET @user_where =CONCAT( " AND  user_id  IN (",userIds,") ");
	  SET @ref_user_where =CONCAT( " AND  opay.cashier_id IN (",userIds,") ");
 END IF;

SET @statement1 = 'SELECT    Tx.id AS tax_id,Tx.tax1_percentage,Tx.name AS tax_name,
 ROUND(CASE WHEN T1.tax1_amount IS NULL THEN 0 ELSE	T1.tax1_amount END - 
				CASE WHEN T2.tax1_amount IS NULL THEN 0 ELSE T2.tax1_amount END, 2) AS tax1,
	ROUND(CASE WHEN T1.tax2_amount IS NULL THEN 0 ELSE T1.tax2_amount END - 
				CASE WHEN T2.tax2_amount IS NULL THEN 0 ELSE T2.tax2_amount END,2) AS tax2,
	ROUND(CASE WHEN T1.tax3_amount IS NULL THEN 0 ELSE T1.tax3_amount END - 
				CASE WHEN T2.tax3_amount IS NULL THEN 0 ELSE T2.tax3_amount END,2) AS tax3,
	 ROUND(CASE WHEN T1.sc_amount IS NULL THEN 	0 ELSE T1.sc_amount END - 
				 CASE WHEN T2.sc_amount IS NULL THEN 	0 ELSE T2.sc_amount END, 2) AS sc_amount FROM taxes Tx LEFT JOIN ';
 SET @statement2 ='(SELECT  dtl.tax_id, sum( round( dtl.tax1_amount - dtl.tax1_amount * ohdr.bill_discount_percentage / 100.00,2)) tax1_amount,
		sum(round(dtl.tax2_amount - dtl.tax2_amount * ohdr.bill_discount_percentage / 100.00,2)) tax2_amount,
		sum(round(dtl.tax3_amount - dtl.tax3_amount * ohdr.bill_discount_percentage / 100.00,2)) tax3_amount,
		sum(round(dtl.sc_amount - dtl.sc_amount * ohdr.bill_discount_percentage / 100.00,2)) sc_amount 
		FROM order_dtls dtl JOIN order_hdrs ohdr ON dtl.order_id = ohdr.order_id
		WHERE dtl.is_tax1_applied = 1 AND dtl.is_void <> 1 AND ohdr. STATUS IN (3, 4)   ';
SET @statement2 = CONCAT(@statement2, "AND closing_date = '",pos_date,"' ",@shift_where,@terminal_where,@user_where,
								" GROUP BY  dtl.tax_id)T1 ON Tx.id=T1.tax_id   LEFT JOIN ");

  SET @statement3 ='(SELECT odtl.tax_id, sum(oref.tax1_amount) AS tax1_amount,	sum(oref.tax2_amount) AS tax2_amount,
		sum(oref.tax3_amount) AS tax3_amount,sum(oref.sc_amount) AS sc_amount
		FROM order_refunds oref JOIN order_payments opay ON oref.order_payment_id = opay.id
	JOIN order_dtls odtl ON oref.order_dtl_id = odtl.id
	JOIN order_hdrs ohdr ON odtl.order_id = ohdr.order_id
	WHERE	ohdr. STATUS IN (3, 4)   '; 
SET @statement3 = CONCAT(@statement3, "AND payment_date = '",pos_date,"'",@ref_shift_where,@terminal_where,@ref_user_where,
				" GROUP BY odtl.tax_id)T2 ON   Tx.id=T2.tax_id");

SET @statementwhere=' where (T1.tax1_amount<>0 or T1.tax2_amount<>0 or T1.tax3_amount<>0 or T1.sc_amount<>0
					  or T2.tax1_amount<>0 or T2.tax2_amount<>0 or T2.tax3_amount<>0 or T2.sc_amount<>0) ';
SET @statement1 = CONCAT(@statement1,@statement2, @statement3,@statementwhere);

 PREPARE stmt FROM @statement1;
 EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for getKitchenQueueNo
-- ----------------------------
DROP FUNCTION IF EXISTS `getKitchenQueueNo`;
DELIMITER ;;

CREATE FUNCTION `getKitchenQueueNo`(orderid VARCHAR (50) ,   kitchenId int) RETURNS int(11)
DETERMINISTIC
BEGIN

 DECLARE checkExists INTEGER;  
 DECLARE queueNo INTEGER;  

 SELECT 1  INTO checkExists  FROM order_dtls dtl JOIN sale_items si on dtl.sale_item_id=si.id 
	where   dtl.is_void=0 and dtl.is_printed_to_kitchen=0 
	and si.is_printable_to_kitchen=1 and dtl.kitchen_id=kitchenId and dtl.order_id=orderid COLLATE utf8_general_ci LIMIT 1 ;

 IF (checkExists =1) THEN 
 
		SELECT IFNULL(max(kitchen_queue_no),0) INTO queueNo FROM order_kitchen_queue where kitchen_id=kitchenId;
	SET queueNo=queueNo+1;

	INSERT INTO order_kitchen_queue(order_id,kitchen_id,kitchen_queue_no,printed_time)
		select orderid,kitchenId,queueNo,NOW();
END IF ; 
	RETURN queueNo;
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for sp_resetKitchenQueueNumber
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_resetKitchenQueueNumber`;
DELIMITER ;;

CREATE PROCEDURE `sp_resetKitchenQueueNumber`()
DETERMINISTIC
BEGIN

  SET @max_queue_no = 0;

  SET @delSQL = "DELETE from order_kitchen_queue where order_id NOT in (SELECT order_id from order_hdrs where status in (1,6));";
  PREPARE delStmt FROM @delSQL;
  EXECUTE delStmt;
  DEALLOCATE PREPARE delStmt;

END;;
DELIMITER ;




-- ----------------------------
-- Procedure structure for sp_convertToStockUom
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_convertToStockUom`;
DELIMITER ;;
CREATE    PROCEDURE `sp_convertToStockUom`(`param_code` VARCHAR(10),  param_sale_qty DECIMAL (11,2) , out param_stock_uom_id int, out param_stock_qty DECIMAL (11,2))
DETERMINISTIC
BEGIN

DECLARE saleItemUom, stockItemUom VARCHAR(10);

SELECT

sales_uom.`code` AS sales_uom_code,
stock_uom.`code` AS stock_uom_code,
stock_items.stock_uom_id
INTO saleItemUom,stockItemUom,param_stock_uom_id

FROM
sale_items
LEFT JOIN stock_items ON sale_items.stock_item_id = stock_items.id
LEFT JOIN uoms AS sales_uom ON sale_items.uom_id = sales_uom.id
LEFT JOIN uoms AS stock_uom ON stock_items.stock_uom_id = stock_uom.id


WHERE sale_items.code=param_code;

CALL sp_convertUom(saleItemUom,stockItemUom,param_sale_qty,param_stock_qty);


END;;
DELIMITER ;



-- ----------------------------
-- Procedure structure for sp_convertUom
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_convertUom`;
DELIMITER ;;
CREATE    PROCEDURE `sp_convertUom`(`param_src_uom_code` VARCHAR(10), `param_dest_uom_code` VARCHAR(10), param_uinit DECIMAL(11,2),  out param_value DECIMAL (11,2))
DETERMINISTIC
BEGIN

		DECLARE srcBaseUomCode, destBaseUomCode VARCHAR(10);
		DECLARE srcBaseUomValue, destBaseUomValue DECIMAL(11,2);


		CALL sp_getBaseUomValue(param_src_uom_code,srcBaseUomCode,srcBaseUomValue);
		
		CALL sp_getBaseUomValue(param_dest_uom_code,destBaseUomCode,destBaseUomValue);

	SET param_value=param_uinit*srcBaseUomValue/destBaseUomValue;

END;;
DELIMITER ;



-- ----------------------------
-- Procedure structure for sp_getBaseUomValue
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_getBaseUomValue`;
DELIMITER ;;
CREATE    PROCEDURE `sp_getBaseUomValue`(`param_code` VARCHAR(10), OUT param_base_code VARCHAR(10), out param_base_value DECIMAL (11,2))
DETERMINISTIC
BEGIN

		DECLARE uomId,  isCompound, baseUomId , compoundUnit int;
		DECLARE uomCode,baseUomCode VARCHAR(10);
    DECLARE baseValue DECIMAL (11,2) ;

		SET  max_sp_recursion_depth = 255;
		SELECT uoms.id,  uoms.`code`,  uoms.is_compound,  uoms.base_uom_id,baseUom.code as base_uom_code,  uoms.compound_unit  
					INTO uomId, uomCode, isCompound, baseUomId, baseUomCode, compoundUnit 
					FROM uoms left JOIN uoms baseUom on uoms.base_uom_id=baseUom.id 
					WHERE  uoms.code=param_code and  uoms.is_deleted=0  and ifnull(baseUom.is_deleted,0)=0;

	SET param_base_value=1;
			SET param_base_code=uomCode;

		IF(isCompound=1) THEN

			SET baseValue=1;
				
			CALL sp_getBaseUomValue(baseUomCode,baseUomCode,baseValue);
			SET param_base_value=param_base_value*compoundUnit*baseValue;
			SET param_base_code=baseUomCode;


		END IF;

END;;
DELIMITER ;




-- INSERTING SYSTEM DEFINED DATA
-- DEFAULT STOCK ITEMS

INSERT INTO `stock_items` (`id`, `code`, `name`, `description`, `supplier_product_code`, `stock_item_attributes`, `stock_item_location`, `stock_item_category_id`, `part_no`, `market_valuation_method`, `movement_method`, `uom_id`, `qty_on_hand`, `unit_price`, `optimum_level`, `reorder_level`, `reorder_qty`, `preferred_supplier_id`, `last_purchase_unit_price`, `last_purchase_supplier_id`, `tax_id`, `tax_calculation_method`, `is_service_item`, `is_valid`, `is_manufactured`, `is_sellable`, `qty_manufactured`, `top_consumption_rank`, `created_by`, `created_at`, `updated_by`, `updated_at`, `publish_status`, `is_system`, `is_deleted`, `is_synchable`) VALUES ('1', 'SIGSTK', 'Sale Item Group Stock', 'Sale Item Group Stock', '', 'Default', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '0', '0', '1', '1', '1', NULL, '0', '0', '2016-01-22 07:25:33', '0', '2016-01-30 08:33:49', '0', '1', '0', '1');
INSERT INTO `stock_items` (`id`, `code`, `name`, `description`, `supplier_product_code`, `stock_item_attributes`, `stock_item_location`, `stock_item_category_id`, `part_no`, `market_valuation_method`, `movement_method`, `uom_id`, `qty_on_hand`, `unit_price`, `optimum_level`, `reorder_level`, `reorder_qty`, `preferred_supplier_id`, `last_purchase_unit_price`, `last_purchase_supplier_id`, `tax_id`, `tax_calculation_method`, `is_service_item`, `is_valid`, `is_manufactured`, `is_sellable`, `qty_manufactured`, `top_consumption_rank`, `created_by`, `created_at`, `updated_by`, `updated_at`, `publish_status`, `is_system`, `is_deleted`, `is_synchable`) VALUES ('2', 'NSTKI', 'Non Stock Item', 'Non Stock Item', '', 'Default', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '0', '0', '1', '1', '1', NULL, '0', '0', '2016-01-22 07:25:33', '0', '2016-01-30 08:33:49', '0', '1', '0', '1');

-- sync table terminal_sync_table_settings
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('1', '1', '1', 'order_hdrs', '0', '', 'order_hdrs.order_id', 'sync_status, sync_message,last_sync_at', 'order_id', 'order', '0', '1', '0', '1', 'Order Items', '2018-04-30 21:19:27');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('2', NULL, '2', 'order_dtls', '1', 'order_hdrs.order_id = order_dtls.order_id', 'order_dtls.id', 'last_sync_at', 'id', '', '0', '1', '0', '1', 'Order Items', '2018-04-30 21:19:30');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('3', NULL, '3', 'order_payments', '1', 'order_payments.order_id = order_hdrs.order_id', 'order_payments.id', 'last_sync_at', 'id', NULL, '0', '1', '0', '0', 'Order Items', '2018-04-30 21:19:30');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('4', NULL, '4', 'order_discounts', '1', 'order_discounts.order_id = order_hdrs.order_id', 'order_discounts.id', 'last_sync_at', 'id', NULL, '0', '1', '0', '0', 'Order Items', '2018-04-30 21:19:33');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('5', NULL, '5', 'order_serving_tables', '1', 'order_serving_tables.order_id = order_hdrs.order_id', 'order_serving_tables.id', 'last_sync_at', 'id', NULL, '0', '0', '0', '1', 'Order Items', '2018-04-30 21:19:34');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('6', NULL, '6', 'order_serving_seats', '1', 'order_serving_seats.order_id = order_hdrs.order_id', 'order_serving_seats.id', 'last_sync_at', 'id', NULL, '0', '0', '0', '1', 'Order Items', '2018-04-30 21:19:36');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('7', '2', '7', 'txn_staff_attendance', '0', '', 'txn_staff_attendance.shift_start_date, txn_staff_attendance.shift_start_time', 'sync_status,sync_message,last_sync_at', 'id,pos_id', 'attendance', '0', '1', '0', '0', 'Attendance', '2018-04-30 21:19:47');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('8', '3', '8', 'cashier_shifts', '0', '', '', 'sync_status,sync_message,last_sync_at', 'pos_id,cashier_id,shift_id', 'shift', '0', '1', '0', '0', 'Cashier Shift', '2018-04-30 21:20:02');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('9', '4', '9', 'shift_summary', '0', '', '', 'sync_status,sync_message,last_sync_at', 'auto_id,station_code', 'shift_summary', '0', '1', '0', '0', 'Shift Summary', '2018-04-30 21:20:15');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('10', NULL, '10', 'pos_invoice', '1', 'order_hdrs.order_id = pos_invoice.order_id', 'pos_invoice.order_id', NULL, 'id', '', '0', '1', '0', '0', 'Order Items', '2018-04-30 21:20:24');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('11', '5', '11', 'txn_cashouts', '0', '', '', 'sync_status,sync_message,last_sync_at', 'id,station_id', 'txn_cashouts', '0', '1', '0', '0', 'Daily Cashouts', '2018-04-30 21:20:45');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('12', '12', '12', 'order_refunds', '0', '', 'order_refunds.order_id', 'sync_status,sync_message,last_sync_at', 'id', 'order_refunds', '0', '1', '0', '0', 'Order Refund', '2017-03-11 14:44:57');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('13', NULL, '13', 'order_payment_hdr', '1', 'order_payment_hdr.order_id = order_hdrs.order_id', 'order_payment_hdr.id', 'last_sync_at', 'id', NULL, '0', '1', '0', '0', 'Order Items', '2018-04-30 21:20:27');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('14', NULL, '14', 'order_customers', '1', 'order_customers.order_id = order_hdrs.order_id', 'order_customers.order_id', 'last_sync_at', 'order_id', '', '0', '1', '0', '0', 'Order Items', '2018-04-30 21:20:26');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('100', '100', '100', 'addressbook', '0', '', NULL, 'last_sync_at', 'id,name', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:48');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('101', '101', '101', 'area_codes', '0', '', NULL, 'created_by,created_at,description,last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:51');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('102', '102', '102', 'bank_card_types', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:54');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('103', '103', '103', 'bill_params', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:55');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('105', '105', '105', 'choices', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:55');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('106', '106', '106', 'combo_content_substitutions', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:55');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('107', '107', '107', 'combo_contents', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:56');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('108', '108', '108', 'currencies', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:56');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('109', '109', '109', 'customer_types', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:56');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('110', '110', '110', 'customers', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:57');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('111', '111', '111', 'customers_type_item_prices', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:57');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('112', '112', '112', 'department_itemclass', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:57');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('113', '113', '113', 'departments', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:58');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('114', '114', '114', 'discounts', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:59');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('115', '115', '115', 'employee_categories', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:59');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('116', '116', '116', 'employee_shift_group', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:16:59');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('117', '117', '117', 'employees', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:00');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('118', '118', '118', 'global_item_attributes', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:00');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('119', '119', '119', 'global_roundings', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:01');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('120', '120', '120', 'holidays', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:01');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('121', '121', '121', 'hot_items', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:02');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('122', '122', '122', 'item_class_display_order', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:02');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('123', '123', '123', 'item_classes', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:02');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('124', '124', '124', 'kitchens', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:03');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('125', '125', '125', 'menu_departments', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:03');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('126', '126', '126', 'menus', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:04');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('128', '128', '128', 'order_params', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:04');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('129', '129', '129', 'packages', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:04');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('130', '130', '130', 'payment_modes', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:05');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('131', '131', '131', 'pos_system_functions', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:05');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('132', '132', '132', 'pos_user_group_functions', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:06');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('133', '133', '133', 'reasons', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:06');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('134', '134', '134', 'rounding', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:06');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('135', '135', '135', 'sale_item_choices', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:07');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('136', '136', '136', 'sale_item_discounts', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:07');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('137', '137', '137', 'sale_item_display_order', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:08');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('138', '138', '138', 'sale_item_images', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:08');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('139', '139', '139', 'sale_items', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:09');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('140', '140', '140', 'serving_table_location', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:09');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('141', '141', '141', 'serving_tables', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:09');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('142', '142', '142', 'shift_groups', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:10');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('143', '143', '143', 'shop', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:10');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('144', '144', '144', 'shop_departments', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:11');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('145', '145', '145', 'shop_shifts', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:11');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('146', '146', '146', 'shop_users', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:13');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('147', '147', '147', 'stations', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:12');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('148', '148', '148', 'system_functions', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:14');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('149', '149', '149', 'system_params', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:15');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('150', '150', '150', 'tax_param', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:15');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('151', '151', '151', 'taxes', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:16');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('152', '152', '152', 'uoms', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:16');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('153', '153', '153', 'user_group_functions', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:17');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('154', '154', '154', 'user_groups', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:18');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('155', '155', '155', 'users', '0', '', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', '', '2016-03-21 18:17:18');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('156', '156', '156', 'terminal_sync_table_settings', '0', ' ', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', ' ', '2016-03-21 18:17:19');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('157', '157', '157', 'sale_item_combo_contents', '0', ' ', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', '0', ' ', '2017-01-22 11:36:16');
INSERT INTO `terminal_sync_table_settings` (`id`, `sync_order`, `table_id`, `table_name`, `parent_table_id`, `table_criteria`, `order_by`, `column_to_exclude`, `pkey`, `web_param_value`, `is_sync_server_to_terminal`, `is_sync_terminal_to_server`, `is_sync_terminal_to_tab`, `is_sync_tab_to_terminal`, `remarks`, `last_sync_at`) VALUES ('158', '158', '158', 'sale_item_combo_substitutions', '0', ' ', NULL, 'last_sync_at', 'id', NULL, '1', '0', '1', NULL, ' ', '2016-05-25 15:23:02');


-- counters
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('addressbook', 'addressbook', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('arcompany', 'arcompany', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('areacode', 'areacode', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('bank_card_types', 'bank_card_types', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('choices', 'choices', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('combo_content_substitutions', 'combo_content_substitutions', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('combo_contents', 'combo_contents', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('currency', 'currency', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('customer', 'customer', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('customertype', 'customertype', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('delivery_note', 'delivery_note', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('department', 'department', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('discount', 'discount', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('duty_roster', 'duty_roster ', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('employee', 'employee', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('employee_shift_group', 'employee_shift_group', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('employeecategory', 'employeecategory', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('flash_message_dtls', 'flash_message_dtls', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('flash_messages', 'flash_messages', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('invoice', 'invoice', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('item_class_display_order', 'item_class_display_order', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('itemclasses', 'itemclasses', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('kitchen', 'kitchen', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('menu', 'menu', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('menu_departments', 'menu_departments', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('package', 'package', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('po_template', 'po_template', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('pos_user_group_functions', 'pos_user_group_functions', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('POST', 'order_bill_no', '0');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('pr_return', 'pr_return', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('purchaserequest', 'purchaserequest', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('purchasereturn', 'purchasereturn', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('reason', 'reason', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('rounding', 'rounding', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('sale_item_choices', 'sale_item_choices', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('sale_item_combo_contents', 'sale_item_combo_contents', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('sale_item_combo_substitutions', 'sale_item_combo_substitutions', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('sale_item_display_order', 'sale_item_display_order', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('saleitem', 'saleitem', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('saleitem_customer_types', 'saleitem_customer_types', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('saleitem_discounts', 'saleitem_discounts', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('saleitem_images', 'saleitem_images', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('saleitem_tags', 'saleitem_tags', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('sales_order', 'sales_order', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('serving_table', 'serving_table', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('serving_table_location', 'serving_table_location', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('serving_table_images','serving_table_images','100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('shift_groups', 'shift_groups', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('shipment', 'shipment', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('shop', 'shop', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('shop_departments', 'shop_departments', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('shop_transfer', 'shop_transfer', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('shopshift', 'shopshift', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('station', 'station', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stock_item_categories', 'stock_item_categories', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockadjustment', 'stockadjustment', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockdisposal', 'stockdisposal', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockin', 'stockin', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockitem', 'stockitem', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockitem_bom', 'stockitem_bom', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockitem_storages', 'stockitem_storages', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockitemlocation', 'stockitemlocation', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stockout', 'stockout', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('storage', 'storage', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('supplier', 'supplier', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('supplierinvoice', 'supplierinvoice', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('system_function', 'system_function', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('target_settings', 'target_settings', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('taxes', 'taxes', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('taxgroup', 'taxgroup', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('uom', 'uom', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('user', 'user', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('user_group_functions', 'user_group_functions', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('mrp_user_group_functions', 'mrp_user_group_functions', '120');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('usergroup', 'usergroup', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('voucher_class', 'voucher_class', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('vouchertype', 'vouchertype', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('warehouse', 'warehouse', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('stock_in', 'stock_in', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('mrp_item_category', 'mrp_item_category', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('mrp_profit_category', 'mrp_profit_category', '100');
INSERT INTO `counter` (`module`, `key_name`, `key_value`) VALUES ('mrp_department', 'mrp_department', '100');

-- system_functions 

INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('5', 'address_book', 'address_book', 'address_book', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('6', 'department', 'department', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('8', 'shop', 'shop', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('9', 'areacode', 'areacode', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('10', 'station', 'station', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('13', 'kitchen', 'kitchen', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('14', 'currency', 'currency', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('15', 'rounding', 'rounding', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('16', 'holidays', 'holidays', '', 'Settings', 'Setup', '1', '0', '1', '0', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('17', 'storage', 'storage', '', 'Settings', 'Stock', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('18', 'package', 'package', '', 'Settings', 'Stock', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('19', 'uom', 'uom', '', 'Settings', 'Stock', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('20', 'shipment', 'shipment', '', 'Settings', 'Stock', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('21', 'category', 'category', '', 'Settings', 'Stock', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('22', 'reason', 'reason', '', 'Settings', 'Promotions', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('23', 'discount', 'discount', '', 'Settings', 'Promotions', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('24', 'vouchers', 'vouchers', '', 'Settings', 'Promotions', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('25', 'users', 'users', '', 'Settings', 'Users', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('26', 'invoice', 'invoice', '', 'Sales', 'Invoice Sales', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('28', 'customer_type', 'customer_type', 'customer_type', 'Settings', 'Customer', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('29', 'suppliers', 'suppliers', '', 'Settings', 'Supplier', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('30', 'customers', 'customers', '', 'Settings', 'Customer', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('31', 'stockitem', 'stockitem', '', 'Settings', 'Stock', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('32', 'creditcard', 'creditcard', '', 'Settings', 'Payment', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('33', 'itemclass', 'itemclass', '', 'Settings', 'Products', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('34', 'salesitem', 'salesitem', '', 'Settings', 'Products', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('35', 'hotitem', 'hotitem', '', 'Settings', 'Products', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('36', 'comboitem', 'comboitem', '', 'Settings', 'Products', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('37', 'usergroup', 'usergroup', '', 'Settings', 'Users', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('38', 'employee_categeory', 'employee_categeory', '', 'Settings', 'People', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('39', 'employee', 'employee', '', 'Settings', 'People', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('40', 'shopshift', 'shopshift', '', 'Settings', 'People', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('41', 'shiftgroup', 'shiftgroup', '', 'Settings', 'People', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('42', 'sales_order', 'sales_order', '', 'Sales', 'Invoice Sales', '1', '1', '1', '1', '1', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('43', 'delivery_note ', 'delivery_note', '', 'Sales', 'Invoice Sales', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('44', 'purchase_request', 'purchase_request', '', 'Stock', 'Purchasing', '1', '1', '1', '1', '1', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('45', 'purchase_order', 'purchase_order', '', 'Stock', 'Purchasing', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('46', 'supplier_invoice', 'supplier_invoice', '', 'Stock', 'Purchasing', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('47', 'purchase_return', 'purchase_return', '', 'Stock', 'Purchasing', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('48', 'stock_in', 'stock_in', '', 'Stock', 'Stock Control', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('49', 'stock_out', 'stock_out', '', 'Stock', 'Stock Control', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('50', 'stock_disposal', 'stock_disposal', '', 'Stock', 'Stock Control', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('51', 'stock_adjustment', 'stock_adjustment', '', 'Stock', 'Stock Control', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('52', 'duty_roaster', 'duty_roaster', '', 'People', 'People', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('53', 'staff_transfer', 'staff_transfer	', '', 'People', 'People', '1', '0', '0', '0', '1', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('54', 'staff_attendence', 'staff_attendence', '', 'People', 'People', '1', '1', '1', '1', '1', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('55', 'staff_leave', 'staff_leave', '', 'People', 'People', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('56', 'tax', 'tax', 'tax', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('57', 'system_settings', 'system_settings', '', 'Settings', 'Setup', '1', '0', '1', '0', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('58', 'menu', 'menu', '', 'Settings', 'Products', '1', '1', '1', '1', '1', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('59', 'purchase_request_app', 'purchase_request_approval', 'purchase_request_approval', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('60', 'purchase_request_rej', 'purchase_request_rejected', 'purchase_request_rejected', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('61', 'purchase_request_pro', 'purchase_request_processed', 'purchase_request_processed', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('62', 'purchase_request_clo', 'purchase_request_closed', 'purchase_request_closed', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('63', 'purchase_order_appro', 'purchase_order_approval', 'purchase_order_approval', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('64', 'purchase_order_rejec', 'purchase_order_rejected', 'purchase_order_rejected', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('65', 'purchase_order_close', 'purchase_order_closed', 'purchase_order_closed', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('67', 'stock_in_approval', 'stock_in_approval', 'stock_in_approval', 'Stock', 'Stock Control', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('68', 'stock_adjustment_app', 'stock_adjustment_approval', 'stock_adjustment_approval', 'Stock', 'Stock Control', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('69', 'stock_out_approval', 'stock_out_approval', 'stock_out_approval', 'Stock', 'Stock Control', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('70', 'purchase_return_appr', 'purchase_return_approval', 'purchase_return_approval', 'Stock', 'Purchasing', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('71', 'DSH', 'dashboard_hq', 'dashboard_hq', 'Dashboard', 'Dashboard', '1', NULL, NULL, NULL, NULL, '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('72', 'SST', 'shop_sales_target', 'shop_sales_target', 'Utilities', 'Utilities', '1', '1', '1', NULL, NULL, '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('73', 'cashier shift ', 'cashier_shift ', '', 'Utilities', 'Shop Transactions', '1', '0', '0', '0', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('74', 'orders', 'orders ', '', 'Utilities', 'Shop Transactions', '1', '0', '0', '0', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('75', 'flash_message', 'flash_message', '', 'Utilities', 'Utilities', '1', '1', '1', '1', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('76', 'daily_sales', 'daily_sales', 'Daily Sales', 'Utilities', 'Shop Transactions', '1', '0', '1', '0', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('77', 'system_logs', 'system_logs', '', 'Utilities', 'System Logs', '1', '0', '1', '1', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('78', 'serving_table', 'serving_table', '', 'Utilities', 'Serving', '1', '1', '1', '1', '1', '1', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('79', 'inter_shop_transfer', 'inter_shop_transfer_sale_items', '', 'Sales', 'Intershop Transfer', '1', '1', '1', '0', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('80', 'inter_shop_transfer', 'inter_shop_transfer_stock_items', '', 'Sales', 'Intershop Transfer', '1', '1', '1', '0', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('81', 'export_stock_take', 'export_stock_take_sheet', 'export_stock_take_sheet', 'Stock', 'Stock Control', '1', '1', '1', '0', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('82', 'PO Template', 'po_template', '', 'Utilities', 'Utilities', '1', '1', '1', '1', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('83', 'weekly_sales', 'weekly_sales', '', 'Utilities', 'Shop Transactions', '1', '0', '0', '0', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('84', 'SYC', 'sync', 'Synchronisation', 'Utilities', 'Utilities', '1', NULL, NULL, '1', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('85', 'PUB', 'publish', 'Publish Datas', 'Utilities', 'Utilities', '1', NULL, '1', NULL, NULL, '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('86', 'STCS', 'staff_cost_summary', 'Staff Cost Summary', 'Utilities', 'Shop Transactions', '1', '0', '0', '0', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('87', 'sales_summary', 'sales_summary', 'sales_summary', 'Manager', '', '1', '1', '1', '1', '0', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('88', 'day_end_report', 'day_end_report', '', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('89', 'hourly_report', 'hourly_report', '', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('90', 'daily_sales_report', 'daily_sales_report', '', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('91', 'master_sales_report_', 'master_sales_report_hq', 'master_sales_report_hq', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('92', 'consumption_report', 'consumption_report', '', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('93', 'master_sales_report', 'Master Sales Report', 'Master Sales Report', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('94', 'weekly_sales_and_ban', 'weekly_sales_and_banking_report', '', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('95', 'attendance_report', 'attendance_report', '', 'Attendance', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('96', 'dashboard_shop', 'dashboard_shop', 'dashboard_shop', 'Dashboard', 'Dashboard', '1', NULL, NULL, NULL, NULL, '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('97', 'sales_report_by_grou', 'Sales Report By Groups', 'Sales Report By Groups', 'Sales', 'Reports', '1', NULL, NULL, NULL, '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('98', 'daily_saless', 'daily_sales_item_wise', 'daily_sales_item_wise', 'Utilities', 'Shop Transactions', '1', '0', '1', '0', '0', '0', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('99', 'weekly_dashboard', 'weekly_dashboard', 'weekly_dashboard', 'Utilities', 'Sales Summary', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('100', 'choices', 'choices', 'choices', 'Settings', 'Products', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('101', 'saleitem_choices', 'saleitem_choices', '', 'Settings', 'Products', '1', '1', '1', '1', '0', '1', '1', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('102', 'daily_expense', 'daily_expense', 'daily_expense', 'Sales', 'Reports', '1', '0', '0', '0', '1', '0', '0', '0', '2015-05-27 17:34:51', '0', '2015-05-27 17:34:51');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('103', 'mrp_item_category', 'mrp_item_category', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2017-10-06 11:54:18', '0', '2017-10-06 11:54:18');
INSERT INTO `system_functions` (`id`, `code`, `name`, `description`, `system_group`, `system_sub_group`, `is_view_applicable`, `is_add_applicable`, `is_edit_applicable`, `is_delete_applicable`, `is_execute_applicable`, `is_publish_applicable`, `is_export_applicable`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES ('104', 'mrp_profit_category', 'mrp_profit_category', '', 'Settings', 'Setup', '1', '1', '1', '1', '0', '1', '1', '0', '2017-10-06 11:42:18', '0', '2017-10-06 11:42:18');
INSERT INTO `system_functions` (id, code, name, description, system_group, system_sub_group, is_view_applicable, is_add_applicable, is_edit_applicable, is_delete_applicable, is_execute_applicable, is_publish_applicable, is_export_applicable, created_by, created_at, updated_by, updated_at)
VALUES(105, 'tally_export', 'tally_export', 'tally_export', 'Sales', 'Reports', 1, 0, 0, 0, 1, 0, 0, 0, '2018-02-26 17:34:51.000', 0, '2018-02-26 17:34:51.000');
INSERT INTO `system_functions` (id, code, name, description, system_group, system_sub_group, is_view_applicable, is_add_applicable, is_edit_applicable, is_delete_applicable, is_execute_applicable, is_publish_applicable, is_export_applicable, created_by, created_at, updated_by, updated_at)
VALUES(106, 'tax_report', 'tax_report', 'tax_report', 'Sales', 'Reports', 1, 0, 0, 0, 1, 0, 0, 0, '2018-02-26 17:34:51.000', 0, '2018-02-26 17:34:51.000');
INSERT INTO `system_functions` (id, code, name, description, system_group, system_sub_group, is_view_applicable, is_add_applicable, is_edit_applicable, is_delete_applicable, is_execute_applicable, is_publish_applicable, is_export_applicable, created_by, created_at, updated_by, updated_at) VALUES(107, 'mrp_department', 'mrp_department', 'mrp_department', 'Settings', 'Setup', 1, 0, 0, 0, 1, 0, 0, 0, '2018-02-26 17:34:51.000', 0, '2018-02-26 17:34:51.000');
INSERT INTO `system_functions` (id, code, name, description, system_group, system_sub_group, is_view_applicable, is_add_applicable, is_edit_applicable, is_delete_applicable, is_execute_applicable, is_publish_applicable, is_export_applicable, created_by, created_at, updated_by, updated_at, last_sync_at) VALUES(108, 'daily_sales_summary', 'daily_sales_summary', 'daily_sales_summary', 'Sales', 'Reports', 1, 0, 0, 0, 1, 0, 0, 0, '2018-02-26 17:34:51.000', 0, '2018-02-26 17:34:51.000', '2018-09-25 16:47:30.000');
INSERT INTO system_functions (id, code, name, description, system_group, system_sub_group, is_view_applicable, is_add_applicable, is_edit_applicable, is_delete_applicable, is_execute_applicable, is_publish_applicable, is_export_applicable, created_by, created_at, updated_by, updated_at)
VALUES(109, 'bill_wise_report', 'bill_wise_report', '', 'Sales', 'Reports', 1, 1, 1, 1, 0, 1, 1, 0, '2017-10-06 11:54:18.000', 0, '2017-10-06 11:54:18.000');
INSERT INTO system_functions (id, code, name, description, system_group, system_sub_group, is_view_applicable, is_add_applicable, is_edit_applicable, is_delete_applicable, is_execute_applicable, is_publish_applicable, is_export_applicable, created_by, created_at, updated_by, updated_at)
VALUES(110, 'waiter_wise_report', 'waiter_wise_report', '', 'Sales', 'Reports', 1, 1, 1, 1, 0, 1, 1, 0, '2017-10-06 11:54:18.000', 0, '2017-10-06 11:54:18.000');
INSERT INTO `system_functions` VALUES (111, 'location_report', 'location_report', 'Location Wise Report', 'Sales', 'Reports', 1, NULL, NULL, NULL, 1, 0, 0, 0, '2019-12-26 17:34:51', 0, '2019-12-26 17:34:51', '2019-12-26 14:14:37');




-- Serving table default images
INSERT INTO `serving_table_images` (`id`, `image`, `height`, `width`, `is_system`, `is_default`) VALUES ('1', 'iVBORw0KGgoAAAANSUhEUgAAAFAAAABLCAYAAADnAAD1AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH4AkeDQoxxK+IHAAADN5JREFUeNrtnHtQVFeex7/30d236QakuwVpGhrDc0BjWAOiKUbDoDIoIRirNil3ZonGJJY1ESY1O1NjJZUymdSYTHZdC80G48xurNpsFZYaH6WuosEw+Ao+AJGIOPLshqZtaPpB932c/QNCNII00Oz8wf1W3T/69rnn/O7nnN85v3vrnh+FJ8oIoPvhEzSAUAChGk2EUq1W5mq1mgSVShnqdruXaDQaAAQA4PF4nSoVd8fn81m8Xv/lvj7LHQBegPIAxElRHhCSQgEdZIx2piotAA0AZVSUMU2t5tJYlknhed6oUCiiGYYBALjdbnAc1yAIYo/L5W71+fz/OzjoEAH0A3APV/V7AB9iItuoAOGlAciKiNBl5OTkLExKSlxgNBrnRkdHIzU1FVFRUWAYBlFRUaNXd3V1QRAEtLe34c6dFnR1daKzs/v2hQsXbnz3XXMNgG9jYn55vavrC36a0OYCyASw+NlnM7Oys5ekx8XFzTcYDEhISIDZbAbLsoiJiRm9oKenB5IkwWq1orm5GVarFW1tbT1Xrly9dvFibR2AGgDnwsNXCgMDZ6bdwZsLCwsHzp4967dYLKS/v58IgkCmIq/XS3p6esi1a9eEN9/c0g/gwDThJZtMsQ0HDhxw37t3j9hsNuLz+aZkG8/zpK+vj7S0tJAPP/zQrdPpWgEuKhhu8Yf9+/eTmZBGo7kJgJmGbRnFxcU9M2Hb6tWrCYCFExlATWyjItRgCP/2m2++SU5NTR0929fXB4vFArvdDrfbjZ4eK0RRAkVRoChq1HX0ej3mzZuHuXPnPlJrYWEhjh8/XgDg5DQ7eO9nn3225fXXXx89wfM8urq60NbWBlEUYbF0w+sdGpmfKej1euh0Omg0GkRHR8NgMEClUo1e/8UXX+DVVzf+WZLETeOsBZOdA+m0NWt+fqu0tBSHDx/G2bNn/T6f/4IkSR0Mo7guCILP6XRSer2xxufzaFyu/gydTk8AohJFPkOhUIYyDLU6KysrtKCgAHa7Hb/97e/+2+v1bJjIwImlV82ZIzXt21fxVHNzMyorK+FwOO4zjOIiz/N3VSp1h8PhUCiVXINSybmczgdpajWnDQkJoXnel6pSqUJFUciNj4835efnM8uWLcNbb73Vdf16UybgswTDhcGy0TSAIgD2hoYGsnPnTheAn0ymDoqiD9++fZusX7/eD2AXkMD+AG/aFiYCOP/RRx+R5uZmCcC/Pl5mCQUk0j/8Jg//ufSDDz4gBw8eJADu/HBvxgntYwMJYwTBIgG4AOCqyWRabTabNQCWAHAGeIeGvLyfJaSmpiI6OnoQwGmgVQNgYHjkTWUEPnyN4AXwHxzHZaekpHAxMaanu7o65wOQRgpIwOVxnU+vn7tk+fLlGBgYAIATAKzD/0xsU0AuTNP0hc2bN6c3NjaqV61apTabzdi1a5crLy/PK0lkwkYslm5GpVKFl5aWMps2bZJWrFgx8OWX/yN1d3flA/h2uovcqlWr3pw/fz7tdrvDy8rKqO3bt/MxMTED4eFzJjSOpimcOXMm5P3339dUVlZCEASXzWYTzp//+s+iKLwdDA/OWrFiBSGEkI6ODlJcXExeeOEFUl5eHvCKdv/+ffLSSy+RoqIi8u677xJCCLl58yYB8F9BsK+eEEIEQSBlZWXkxRdfJCUlJcRqtQZsX2lpKSkqKiLr1q0jPM8TQgiJiNDdRJD0D2vXrg16mHDp0iUC4PMg2Hd9JsKYiAjdtUAapwMpRAjBDCkYFfvwdxQLRDCAwxAfn6BRq9WQJEITQigAYBhWvHv3rlEQBAwODsLlcn0/J4IQMmWwLMvCbrcDQGhW1jLj4OAAN8kqKIqiJLPZ5Dx58pT04MED+HzB46jVasGyrHL58tzI3t5e7Y/72efz4969Fg8Q2U8pFGHP5OYuPV1U9EIkyyrGmGRpMAwDl8sFihpec1QqFURRhCAIUxt2hIBlWajVavj9/qkPX0KgVqsxMDAwaltQ3IIQhIWFjdspoiji6NGj3SdPVuexsbFRK997773I7OxsyApc8fHxxubmu8/SNE0lxMXFyUQmKaVSCZqmFTQAIkmSTGSKomUEMwSwsbERVVVVGBwcnLVw+vv7ce7cOVy5cmXciGNMgOfPn0dVVRU4jsO+fftmLcA9e/aA4zi0tLTgyJEjgQOsqqrCtm3b8Nxzz33/gD0rZbfbsWzZMmzYsGFcgGO+jSkuLsaOHTtQUFCAyMjIWQswPT0dNTU1+Prrr7Ft27bAAS5evBiSJKGpqQlvvPHGrAVYUlKC6upqrFu3DmlpaZN7H5iZmYnMzMxZvcIyDIPc3Fw5jJHjQBmgDFCWDFAGKAOUAcqSAcoAZYAyQFkyQBmgDFAGKEsGKAOUAcoAZYAyAhmgDFAGKAOUJQOUAcoAZYCyggiwtrYWn376KYaGhmYtHI/Hg127duHEiROTA1hTU4OvvvoKOTk52L9//6wFuHv3bhQWFqK9vR3Hjh0LHODp06exc+dOLFiwAO3t7bMWYHd3NxISErBlyxYcPXp0zDJjfmD58ssvo6SkBPn5+TAajbMWYHp6Oj7//HPU19dj69atYxdKTEz+tKOj47Htnp2dneTQoUNEkiQym1VfX0+6u7sfO19VVUUSElI2jvuJb0xMDIqLi2f9Krtw4cIJV2EqmDsdZ10YQwixz+a9IFOVKIogRBJZm81+ds+ePb9fv379uHtuFQoFvF5v0PYLA8N7ckNCQuD3+xBQ/p9xxHHcI3uZpyqGYUbrIISA4zjwvB8ABVEUHykrSRIqKyuHLJbeaxRAsWFhujy9XhdP09RjG8IEQXSbzTEH3nnnHXi93uFhO80d6993ytq1hecTE1P2CwKvnUofMAw71Nr63QcnTpyInc7Gbb/fj8bGxtH74zgO5eXlYFnlZ4RIGo0m7BJNU8JDAIndbr/udDoCyziyZs2aGVnhAFRM7Zb/jQJWfR/D3pgJ28LCwgIaHQ9lD+p+orvNkMbxu4kS8ZQR/JDIYEaMC/SeR3rxyRl6WJb9f34Wn1QWI2YmOjnQOTUgMqdOnWqvq6szxsbGktjYWEVubi7q6ur41tZWD8/zlCiK7OP2E0oQBAYA1dnZSb322mtMXFwcZbFYsG/fPrGyspIH8NfARtwT9dfy8vLUTZs2ITs7m42Pj6fq6urw8ccfC8uXLw8gcxGN7du3i3V1dZxCoUBBQYFQVFQ0JAhCUxBsG1UYgF8A2F1aWkpsNhvJysr6dwAZGM6htRpA/o+ONQA2APglgF/o9fqrhBDyySef8AB2A3g+GCMlLCxOO9JO6VNPPWUlhJC3337bAeB3AF4daX+8458B/BOAXx85coT09/cTANcBPD2t0ODRuUj98ImPa2trSXV1NVm48OmVk3250dbWJtXV1ZER40cUNQ3bHtEzZWVlPSPJEy8BmDOJyhbt2PG+hxBCkpOT6wGM5LHRBTZ/jK9BAAKAOZxeH7o3Pt78q6amJjQ1NYlnzpw5OFJADUAx0uhYhwaAmqaZ53t7e7JOnTpF+Xy+4oGBQTVAzo7mfJ20QgEMQqlcT4ti60+joyMviaKo6erqwqFDh5s8Hs85DCelDRk5ND86tADCAUSEhoYbrdbu/KtXr4Z1dHREKRTKJS6XdAJweqe4Cj4Wtb20eXPJwYqKCjgcDly+fBn37t2Dw+Hwut0eh9fr7aFpmrjdbni9XhgMBlAUJQkCT6ekpIT4fD6D0Wg0mM1mZGRkQKlUIi8vD1VVVT8DcG56XsIaTaZ59Q0NDXqtVosrV66gpaUFnZ2d4Hn+QV9fXw/HcR5CAJvNBp1OB4ZhwPO8KiJizjyapnVRUVF0SkoKkpKSYDKZsHfvXmzd+qs/AdJvguDG0crIyEi73W4fM14SRZEMDQ2RoaEh4vF4iMvlGv09NDQ0bpxls9mIUqm6NezDf5mOgV8eP3583HYetuXHtj3pTdPIAvRMMObptI0bNxKPx/NEIJORx+MhNpuNvPLKK00AEqYTBoWGhl51OBxkcHAwKLbxPE+cTiepqKggI4tQMFwY/7J06bKMzMzMBSZTzIJFixYhLS0NJpMp4Dt1OByor69HbW0t+dvf7n977Nixi1arZQ+GU25O5WEQAA8AP4mKit62cmXegqSkxMXp6encokWLkJiYOKlHudu3b+Py5cu4detWR2Pjrdpz56puAPjjNAGqAYzOo+kAfgpQKVqtdh3HqWI1Gg2SkpKQk5ODsLAwEEIwf/58tLW1AQB8Ph9u3LiB6upqiKIIr3fI6nQ6jwPkBoD/xOgK8kg7kxuCtEohSfw/AtICAGqNRvsMx6mWqlQqhcFgwNKlS5GcnAyapqHX6wEMZ+OQJAl37tzBxYsX0dvbC5/P53K7Pcf8fp8VoGwAWwHw9ona/z9mFk/Xxd0oOgAAAABJRU5ErkJggg==', '75', '80', '1', '1');
INSERT INTO `serving_table_images` (`id`, `image`, `height`, `width`, `is_system`, `is_default`) VALUES ('2', 'iVBORw0KGgoAAAANSUhEUgAAAEYAAABGCAYAAABxLuKEAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH4AkeDQ4eCxJwQQAAA2VJREFUeNrt3M1O41YUB/D/vbaDEzsQRTFjIqEMKiKEiLBCXcEjgGYzi6qoUh+Bfd+Eolmyn4dopBA+xOygAiSihATnYwxy/Hm7aYOqdsLIsxmL819aliX/dHzu2RwzYCGbSj2WhfB+AhAgfiJAFIUQbwFISF4YgJ4Q7EMUZT5CUfQf9/f3A8/zxGuPZVlib29PcK78zDRN/zAY9H9RFAUUIIoiaJr2J9/Z2SkTynM459ja2vqBh2EYEce/E4YhODF8oXKIgGAAAL7vQwjx4n3ySzc4joNer4cwDOH7PrLZLAzDgCzLiQKxbRvD4RC2bQMADMNAPp+HJEnxKubh4QHHx8ewbRuj0QgnJydot9uJq5TLy0ucn59DCDF5j8fHx3gV4/s+bNvG2toaVldXAQCNRmOinpRYlgVZlrG9vY3Z2VkAQL1eRxAE8WCEEPA8D5wnuxUJIeD7PqLoeTIZj8cIwzAeTCqVgmmaaDabcF138sBSqZQomEKhgE6ng3q9DsMwEAQBJElCOp2O33wLhQJqtdrk8ymVSpifn09c1aysrEDTNLiuiyAIUKlUkM1m48PIsozFxcXEH9OpVApLS0s0x9CARzAEQzAEQzAEQzAEQyEYgiEYgiEYgiEYgiEYgiEYCsEQDMEQDMEQDMEQDMEQDMFQCIZgCIZgCIZgCIZgCIZgXk2+ahur1+vh/v4ejDHk83ksLCwk7kXDMMTt7S08z4PneVheXkYmk4lfMd1uF2dnZ2CMAQCurq7Q6XQSB3Nzc4Pr62swxuC6Lk5PT6cucvGXlFutFjRNQ7VaRbVahaqqGAwGiUIZDAYYDoeo1Wool8vY3NyE67pwHCfep8QYg67r/1nk+pply+8prutCVVXouj65lslkpi6oTa0YzjlmZmbQarXgOA5GoxEsy4KqqomCMU0T/X4fjUYDANBut9Hv9yftIVbzNU0Tnufh4uICsiyjUqmgWCwmrsesr6/j7u4OzWYTnHNsbGxgbm4uPsw/C1CGYYBzDl3Xp0p/r8nlctA0DU9PT1AUBZqmfftxLUnSVN2kRFEU5HI5GvC+afKVJIlw/ufQYbqe/d22P/9KHM8pFottPh67nw4ODkjj7xweHqLb7f3BgDeFdNr+bXd35/3R0ZH5pZ9AvJYIgWYUqe/+ArOAOlBUC+KiAAAAAElFTkSuQmCC', '70', '70', '1', '0');
INSERT INTO `serving_table_images` (`id`, `image`, `height`, `width`, `is_system`, `is_default`) VALUES ('3', 'iVBORw0KGgoAAAANSUhEUgAAAEoAAABQCAYAAAC+neOMAAAABGdBTUEAALGPC/xhBQAACjppQ0NQUGhvdG9zaG9wIElDQyBwcm9maWxlAABIiZ2Wd1RU1xaHz713eqHNMBQpQ++9DSC9N6nSRGGYGWAoAw4zNLEhogIRRUQEFUGCIgaMhiKxIoqFgGDBHpAgoMRgFFFReTOyVnTl5b2Xl98fZ31rn733PWfvfda6AJC8/bm8dFgKgDSegB/i5UqPjIqmY/sBDPAAA8wAYLIyMwJCPcOASD4ebvRMkRP4IgiAN3fEKwA3jbyD6HTw/0malcEXiNIEidiCzclkibhQxKnZggyxfUbE1PgUMcMoMfNFBxSxvJgTF9nws88iO4uZncZji1h85gx2GlvMPSLemiXkiBjxF3FRFpeTLeJbItZMFaZxRfxWHJvGYWYCgCKJ7QIOK0nEpiIm8cNC3ES8FAAcKfErjv+KBZwcgfhSbukZuXxuYpKArsvSo5vZ2jLo3pzsVI5AYBTEZKUw+Wy6W3paBpOXC8DinT9LRlxbuqjI1ma21tZG5sZmXxXqv27+TYl7u0ivgj/3DKL1fbH9lV96PQCMWVFtdnyxxe8FoGMzAPL3v9g0DwIgKepb+8BX96GJ5yVJIMiwMzHJzs425nJYxuKC/qH/6fA39NX3jMXp/igP3Z2TwBSmCujiurHSU9OFfHpmBpPFoRv9eYj/ceBfn8MwhJPA4XN4oohw0ZRxeYmidvPYXAE3nUfn8v5TE/9h2J+0ONciURo+AWqsMZAaoALk1z6AohABEnNAtAP90Td/fDgQv7wI1YnFuf8s6N+zwmXiJZOb+DnOLSSMzhLysxb3xM8SoAEBSAIqUAAqQAPoAiNgDmyAPXAGHsAXBIIwEAVWARZIAmmAD7JBPtgIikAJ2AF2g2pQCxpAE2gBJ0AHOA0ugMvgOrgBboMHYASMg+dgBrwB8xAEYSEyRIEUIFVICzKAzCEG5Ah5QP5QCBQFxUGJEA8SQvnQJqgEKoeqoTqoCfoeOgVdgK5Cg9A9aBSagn6H3sMITIKpsDKsDZvADNgF9oPD4JVwIrwazoML4e1wFVwPH4Pb4Qvwdfg2PAI/h2cRgBARGqKGGCEMxA0JRKKRBISPrEOKkUqkHmlBupBe5CYygkwj71AYFAVFRxmh7FHeqOUoFmo1ah2qFFWNOoJqR/WgbqJGUTOoT2gyWgltgLZD+6Aj0YnobHQRuhLdiG5DX0LfRo+j32AwGBpGB2OD8cZEYZIxazClmP2YVsx5zCBmDDOLxWIVsAZYB2wglokVYIuwe7HHsOewQ9hx7FscEaeKM8d54qJxPFwBrhJ3FHcWN4SbwM3jpfBaeDt8IJ6Nz8WX4RvwXfgB/Dh+niBN0CE4EMIIyYSNhCpCC+ES4SHhFZFIVCfaEoOJXOIGYhXxOPEKcZT4jiRD0ie5kWJIQtJ20mHSedI90isymaxNdiZHkwXk7eQm8kXyY/JbCYqEsYSPBFtivUSNRLvEkMQLSbyklqSL5CrJPMlKyZOSA5LTUngpbSk3KabUOqkaqVNSw1Kz0hRpM+lA6TTpUumj0lelJ2WwMtoyHjJsmUKZQzIXZcYoCEWD4kZhUTZRGiiXKONUDFWH6kNNppZQv6P2U2dkZWQtZcNlc2RrZM/IjtAQmjbNh5ZKK6OdoN2hvZdTlnOR48htk2uRG5Kbk18i7yzPkS+Wb5W/Lf9ega7goZCisFOhQ+GRIkpRXzFYMVvxgOIlxekl1CX2S1hLipecWHJfCVbSVwpRWqN0SKlPaVZZRdlLOUN5r/JF5WkVmoqzSrJKhcpZlSlViqqjKle1QvWc6jO6LN2FnkqvovfQZ9SU1LzVhGp1av1q8+o66svVC9Rb1R9pEDQYGgkaFRrdGjOaqpoBmvmazZr3tfBaDK0krT1avVpz2jraEdpbtDu0J3XkdXx08nSadR7qknWddFfr1uve0sPoMfRS9Pbr3dCH9a30k/Rr9AcMYANrA67BfoNBQ7ShrSHPsN5w2Ihk5GKUZdRsNGpMM/Y3LjDuMH5homkSbbLTpNfkk6mVaappg+kDMxkzX7MCsy6z3831zVnmNea3LMgWnhbrLTotXloaWHIsD1jetaJYBVhtseq2+mhtY823brGestG0ibPZZzPMoDKCGKWMK7ZoW1fb9banbd/ZWdsJ7E7Y/WZvZJ9if9R+cqnOUs7ShqVjDuoOTIc6hxFHumOc40HHESc1J6ZTvdMTZw1ntnOj84SLnkuyyzGXF66mrnzXNtc5Nzu3tW7n3RF3L/di934PGY/lHtUejz3VPRM9mz1nvKy81nid90Z7+3nv9B72UfZh+TT5zPja+K717fEj+YX6Vfs98df35/t3BcABvgG7Ah4u01rGW9YRCAJ9AncFPgrSCVod9GMwJjgouCb4aYhZSH5IbyglNDb0aOibMNewsrAHy3WXC5d3h0uGx4Q3hc9FuEeUR4xEmkSujbwepRjFjeqMxkaHRzdGz67wWLF7xXiMVUxRzJ2VOitzVl5dpbgqddWZWMlYZuzJOHRcRNzRuA/MQGY9czbeJ35f/AzLjbWH9ZztzK5gT3EcOOWciQSHhPKEyUSHxF2JU0lOSZVJ01w3bjX3ZbJ3cm3yXEpgyuGUhdSI1NY0XFpc2imeDC+F15Oukp6TPphhkFGUMbLabvXu1TN8P35jJpS5MrNTQBX9TPUJdYWbhaNZjlk1WW+zw7NP5kjn8HL6cvVzt+VO5HnmfbsGtYa1pjtfLX9j/uhal7V166B18eu612usL1w/vsFrw5GNhI0pG38qMC0oL3i9KWJTV6Fy4YbCsc1em5uLJIr4RcNb7LfUbkVt5W7t32axbe+2T8Xs4mslpiWVJR9KWaXXvjH7puqbhe0J2/vLrMsO7MDs4O24s9Np55Fy6fK88rFdAbvaK+gVxRWvd8fuvlppWVm7h7BHuGekyr+qc6/m3h17P1QnVd+uca1p3ae0b9u+uf3s/UMHnA+01CrXltS+P8g9eLfOq669Xru+8hDmUNahpw3hDb3fMr5talRsLGn8eJh3eORIyJGeJpumpqNKR8ua4WZh89SxmGM3vnP/rrPFqKWuldZachwcFx5/9n3c93dO+J3oPsk42fKD1g/72ihtxe1Qe277TEdSx0hnVOfgKd9T3V32XW0/Gv94+LTa6ZozsmfKzhLOFp5dOJd3bvZ8xvnpC4kXxrpjux9cjLx4qye4p/+S36Urlz0vX+x16T13xeHK6at2V09dY1zruG59vb3Pqq/tJ6uf2vqt+9sHbAY6b9je6BpcOnh2yGnowk33m5dv+dy6fnvZ7cE7y+/cHY4ZHrnLvjt5L/Xey/tZ9+cfbHiIflj8SOpR5WOlx/U/6/3cOmI9cmbUfbTvSeiTB2Ossee/ZP7yYbzwKflp5YTqRNOk+eTpKc+pG89WPBt/nvF8frroV+lf973QffHDb86/9c1Ezoy/5L9c+L30lcKrw68tX3fPBs0+fpP2Zn6u+K3C2yPvGO9630e8n5jP/oD9UPVR72PXJ79PDxfSFhb+BQOY8/wldxZ1AAAAIGNIUk0AAHomAACAhAAA+gAAAIDoAAB1MAAA6mAAADqYAAAXcJy6UTwAAAAGYktHRAD/AP8A/6C9p5MAAAAJcEhZcwAALiMAAC4jAXilP3YAAAAHdElNRQfgCR4NDxNsuD29AAAN2UlEQVR42u1ce1BUV5r/nXPv7QdNo83ThiCg0EKi4IOo46OCpkJKIBPdkWjiTKqswS2zbsZsNj42rpbGlCSrayKbMQzJuHGS0tk4OpnoaKyIOJiVADGwEAwiorxaDDQCTb9v99k/+kF3gxPJrmG68avqou75zrm3zo/vfK/znUMwepJGR6tzw8MnLgQQCoAhMIgyRrpaWzvOms3mSsAqjmYwGeXHSGxs3D9t2/Yv/56TkwuZTAbGAgMnQgj6+/tRUlLC9u/fvwTAX+7r93Jy8v6to6ODBSrV1dWxKVOm/GLU4jj6/wxooEjR3SRLIpHgvgPlcDAO45BGDRRjbJhes9vtOHbsGDZu3IiGhoYxnVBnZye2bduG/fv3w2q13k+gYu/SNRYACGMOnhBfrM6fPw+Hw4EDBw7gyJEjaGlpGROQzGYzSkpKsHPnTjz33HN455134HA47mFk7PfyKKCgwGQpsJRzWnrtXQZrveXKh6PT6RAb6+yfnJyMwcHBMQGKMQZBECAIAuRyORQKxT0CpXXN1z3nVM6fRwFrJCEDUcCFJ4HwSUCiBFC7OsRTQtYQlxtBnMCSYV9++umnUVlZieeffx5JSUlIT08fE6Dkcjny8vKwadMmvPrqq8jPzwfP875LiFIMDOgzAJoDcDkA91NC/p4456sFEBcGdCYA+B2AQjdYBOAeAuy/Kizcs+nMmc/M5eVfbAMc7wMYADB3zpw5lU888QRsNhsYY+jp6UFhYaFHggKNvv32W7z++uuIi4sDIQSiKOLjjz/u7ejoiFIokmQGw42VmZmZhbt27Yo9fvyE/dCh3y4C8CUALMnNzfuzxWJhBoOBHTz4axYZGfmF673/qtfrffyQDz74gLW3twe0H3Xq1Cmftu3btzMA0QDWbt26lXV3dzPGGLt69aodwEa3Mp89c+bMRziOQ0hICF544R8gkUgWUsqdBLDAZDL56AC73R7wpl4Uh6KXa9euoby8HACqBUE4lJ6ejsjISABAdHQ0TUnRTAdAKccJMxMTExI4zqm/mpubkZOTg46O9ryjR48u02g0IyrNYKGioiKsWrUKjLHJt2/fxkcffeThyWQyzJ8/bxaACXxoqOLvMjIyPMwLFy6wc+fOkejoaGRnZyMlJcXnxQqFAlKpNGCBkUgkUCgUPs9VVVUoLT2Htrb2rqamq1IAKjdQGo3mEQBKKpfLQrzBaGtrJWbzRNWePXsys7KyTrolzU0Gg+H/1ZH7sclqtcJgMHieV61ahba2Nnb8+Im51dXVarud/fHGjRsefkxMjCwm5qEwGhERgYkTJ3oYfX0DnV1dtUYAlwF8WVFREVRLzZ/mzp2LJUuWAECfU62grbW11cN3Wkf2BPWWJrPZDL1ef8vrPTxjDP6eeLCRS7kzAOA44aq3xE2fPh1hYcqp9PHHH/cKeB3gOK7fy/UOboT84n0XaKyrq8vTKAgCCIGcjmDux2V2YMii046enh7Pc0REBETRNpt6x0Imkwnt7e3jGqjQ0Jgb/lZSIpHaaWpqqqdRKpUiIiJiPOMEo1EXM2KapbGx0SdglMlk4xUju1NPW1LDw8N93Amr1cJRfz/JpdTIeEVLECT2SZMmeZ57e3vB88LX9LvvvhvS4hwHk8k49YdkPoOAiFOibJMEQfA0DgwMQBTtJvr555/76Kjw8HCVl0QxQkhQO5zueQOwuR6nJCQkeHjXr1+H2WxpoLdu3fIZpFKpFKmpcz2h3RtvvBHUDufbb7+NkpISAJjpdA8c8xMTEz385uZm3LnT/xdqMpnF5uZmD2PWrFnQ6zvb58+fz15++eXNrpcELbW1taGwsJAUFxefys3NZXFxsfPkcrknS9Ld3d1nNPb3U71ef6GqqtIzMD09HUuWLImoqKjA1q1bQWlwqytRFHH27FloNBqsW7cOeXl5Xq6CEXV19V8D6KMWi7n2ypXGVreHnpKSgrKyMqhUKnN0dLR2y5YtQQ1UUVERlEolW7p06eHly5d/k5+f7xP7/ulPn1QAMFAAFy5f/qpOFEXY7XacPHkS3d3dX/b19ckB7F22bNmQWSBkWLI+EMkV/GNgYAB2ux1Go5EA+BWA5zZv3qwtLS0FAJw9e9YK4CuXSeTkgH3twYMHf33mzBmcO1f6uslk3AegH0CkRqMpz8iYGWa32xnAIIrihOLiYqVarQ5IkBobG/Hzn/+iVyaTGV1OtvTy5a8PGY2DW11dNGFhE17MyVn2j+Xl5VqtVpsAQAQgI5RGyQHsAIRHh16pdP2dR4HFFFhKgFjJk08uKwrkzYX6+no2bVrq88BiAuT6mXPvnSUu3BsLHjAzhyPSCrDdAGFDA9ybgpXe+3g2IJ0GurdACBhwcQTnUOs1d3vvkMDo4VI4DvuQj+nuCPjuDgMAT4M/uvGfs94ZBw9navGARsgejK574KeqKKVgjIn3G6igWHeMMcd9BurB0htXEvUjAPVAokaT23oA1D10JwicAvwHS+//5pUTMMbsD4D6fqDYjwAUeWD1HtBIGYShNm+gRtj5/IPfc76NEGIP8E0ZQil13H3NTADwKAUweQg1LUCIJAzAY1Kp9HMAu4cAy4JSGa8C8CaAYwB+D+CYRqNp0mq1AZuPamxsZBzHVQD4L9ecjgN40RXcAHiFA7D5sceyrsTETKoE4C4eo/kREZElOp2OHTp0iCkUodcAeaSLueW9995zMMaYKIrMarWy999/P+Crgk+cOMFEUWSiKDLGGHvqqadanNIkTyKEVmzfvt1dAW0CkOfSUY7Zubm5j8jlcqxduxbffFOfHBJCugHEA4gqKCggjDFwHAee54Nij49SCo7j4C4nmD59eiSA2YyZXvvNb4rnv/baawCA7OxsKYB5QDrhAYSkpaXOcu9lTZ48GWlpacjMzCw/duyYbPny5fjkk0+CVnW/++67uHHjhnLHjh0XKaUh7e3tHl5oaCjJzHx0zldfVSuoQqHMSEtLk7uZ58+fx+rVq1FcXJyo0+kmDQwMBLWNa2pqwvr167Fr166QdevW4ciRIx6eXC7H4sWLHgYg5+Vy2YIZM2Z4mPX19SgsfKP0/Pky9axZMydfv349NJiBUqlU2Lt3LwoKCs42NzdflclkyywWS4pUKgXP80hKSkoAoKQymVSYMmWKZ2BLSwt6e3X5Z86cXrpnz57//PDDD4Naol566SUkJyez5ubm1QA28rykrLX1pocfERGBmJi4NBoVFeUz0Gaz3QZgAnAbQO/ixYuDGqiwsDB3+bizNJjhalPTNQ8/KSkJCoU8jS5cuNDTaDAYYDAYr8NVfTaOiMFVFcxxQpt3AXB8fDx4nk+hU6dO9YmspVKpaZzGLg4AsFjMgvfJBY7jYLNZJ1Nvv8hVMGb3Q3q8kB0ApNKwry0Wi6dRrVZDIpFE0fj4eG/9hP7+fsd4liiel5pHclBpd3e3p0EQBKhUKjaegTIYumf7Bx+EEFBvMXMiytswPtO9zDl/QVCrh1ItXV1dGBwctFGbzebT22g0KjGOdxE4jggqlcpn2cnlIVdoXV2dp1GhUCA+/qEJ4xkou12c4z4qCwA3b96E2WzupNXV1b4L1eGY4F6vABz9/f3D1mswkdlshvccCSHKuLg4z7OrfLqa9vX1wWg0ehjR0dGJAH4GYAuAvBUrVvi4D3q9PqiA2rdvH+rq6khmZuZBAPskEn6Ztyeg0+lw+7a2lhqNxtu1tbUeRnp6Ordo0aKPKisr9zDG5nqfvmKMobW1NaikSqfTYceOHaS6uvqFmpqaf05PT4/1dpcaGhraANh5vd5wqqam5pcLFiwAAGRkZCAqKooPDw/H0aNH4a/sg+0UQ3JyMvbu3YvS0lI9Y0y5Zs0ajxSYTCZcvPhFFYBe3uEQexsaGuqtVusMiUSC8PBwXLp0CfPm/WRjb2/PwoaGhmeCWXlv2LABWq0Wu3fvXgjgl2+99dZGN6+jo8PR0PDNFwDMFCBdVVXVtWazGYODg8jOzkZ3t25Db29PEYDL3qcaOI4LmlSwN50+fXoAQD2QvOmVVzZ9+uyzzwIADhw4YIbzEDoACBoAqzds2NAXEzOpCkDWUFIrMQzAbwF8BuA0gM80mmk9t27dGpa0b2lpYVVVVZ7rOsaKTCYTq6mpYbW1tcxmsw3jX7lyhfG88DsAL7h+LwLwP8y4Iikp6SLPC0e82qR04sQZEgALAXzv8c/5839yrrOz0+fj165dY2+++Sarra1lO3fuZGO1nWWz2VhxcTErKytjp06dYocPH2YOh2NY+XRaWtqa4TMj8Nv8lHr7kzxgcfT11VsB/Lezybsq2J9CJCPdSHbz5k0888wzSExMhFqtRmNjI8aiYF8URURHRyMrKwsWiwVlZWWwWq3Dbv4Y+fo55j9vn9huhPMaf60qmHeoVOH1Dodjqb/lOHnyJGbPno1Lly6hoKBgTHSPIAiwWCz49NNPwXEcRFH8QRf/jTjzUcaNhDHG++vzxMRErFy5Enfu3MH69euhVCrHKE7jsHLlSjQ1NUEulyMhIWFE4/ND7NEogeLuWkimVqvxt3A+hud5PPzww3flu+qjbKO2lKPNRFBKAjpmppSCEHK/y6cp2ts7JIHsS1ksFjQ2No76uiKvGXsfFLqbQn+IAzoWTJs27fCKFSsi7u1Gwr+lXBOHoqL/+B+DYXAVgFs/AKh7ASkWgAUy2RSJ2VxN4bwTLtACPwKgIzQ0C4ODNQ7nkcRRUyyA2MC9auw+0/8CqjBikj9suucAAAAASUVORK5CYII=', '75', '80', '1', '0');

-- Pos card and barcode scanner patterns
INSERT INTO `pos_scanner_patterns` (`id`, `type`, `description`, `pattern`, `char_prefix`, `char_suffix`) VALUES (1, 1, 'User Card', '^%(\d+)\?$', '%', '?');
INSERT INTO `pos_scanner_patterns` (`id`, `type`, `description`, `pattern`, `char_prefix`, `char_suffix`) VALUES (2, 2, 'Member Card', '^\#(\d+)%$', '#', '%');
INSERT INTO `pos_scanner_patterns` (`id`, `type`, `description`, `pattern`, `char_prefix`, `char_suffix`) VALUES (3, 3, 'Weighing Barcode', '^222(\\d{5})(\\d{1})(\\d{5})$', NULL, NULL);
INSERT INTO `pos_scanner_patterns` (`id`, `type`, `description`, `pattern`, `char_prefix`, `char_suffix`) VALUES (4, 4, 'Order ID Barcode', '([a-zA-Z0-9]{2,})-([a-zA-Z0-9]{2,})-([0-9]{6,})', NULL, NULL);
INSERT INTO `pos_scanner_patterns` (`id`, `type`, `description`, `pattern`, `char_prefix`, `char_suffix`) VALUES (5, 5, 'Tray Barcode', '^444(\\d{5})(\\d{1})(\\d{5})$', NULL, NULL);
INSERT INTO `pos_scanner_patterns` (`id`, `type`, `description`, `pattern`, `char_prefix`, `char_suffix`) VALUES (6, 6, 'Item Tray Barcode', '^333(\\d{5})(\\d{1})(\\d{5})$', NULL, NULL);

-- serving_tables data
INSERT INTO `serving_tables` (`id`, `code`, `name`, `description`, `is_valid`, `covers`, `row_position`, `column_position`, `serving_table_location_id`, `layout_image`, `status`, `created_by`, `created_at`, `updated_by`, `updated_at`, `publish_status`, `is_system`, `is_deleted`, `is_synchable`, `last_sync_at`) VALUES ('1', 'NA', 'NA', NULL, '1', '0', NULL, NULL, '1', '0', '0', '0', '2017-08-16 11:37:25', NULL, NULL, '0', '1', '0', '1', '2017-08-23 12:55:03');

-- serving_table_location data
INSERT INTO `serving_table_location` (`id`, `code`, `name`, `description`, `num_rows`, `num_columns`, `apply_service_charge`, `sc_based_on`, `sc_amount`, `is_sc_percentage`, `apply_service_tax`, `service_tax_id`, `que_no_prefix`, `is_auto_layout`, `bg_image`, `created_by`, `created_at`, `updated_by`, `updated_at`, `publish_status`, `is_deleted`, `is_system`, `is_synchable`, `last_sync_at`) VALUES ('1', 'NA', 'NA', NULL, '0', '0', '0', '1', NULL, '0', '0', NULL, NULL, '1', NULL, '0', '2017-08-16 11:37:25', NULL, NULL, '0', '0', '1', '1', '2017-09-19 12:15:14');

-- Item CAtegery 
INSERT INTO `mrp_item_category` (`id`, `parent_id`, `code`, `name`, `description`, `is_system`, `is_deleted`, `created_by`, `created_at`, `updated_by`, `updated_at`, `publish_status`, `is_synchable`) VALUES ('1', NULL, 'MISC', 'Miscellaneous', NULL, '1', '0', '0', '2017-09-19 12:15:14', NULL, NULL, '0', '1');
