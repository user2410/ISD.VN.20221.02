CREATE TYPE "BIKE_TYPE" AS ENUM (
  'STANDARD_BIKE',
  'STANDARD_EBIKE',
  'TWIN_BIKE'
);

CREATE TYPE "LOCK_STATUS" AS ENUM (
  'LOCKED',
  'RELEASED'
);

CREATE TYPE "INVOICE_OF" AS ENUM (
  'RENTAL',
  'RETURN'
);

CREATE TABLE "Bike" (
  "id" bigserial PRIMARY KEY,
  "licensePlate" varchar NOT NULL,
  "price" integer NOT NULL,
  "type" "BIKE_TYPE" NOT NULL
);

CREATE TABLE "EBike" (
  "id" bigint PRIMARY KEY,
  "batteryLife" float NOT NULL
);

CREATE TABLE "Dock" (
  "id" bigserial PRIMARY KEY,
  "name" varchar NOT NULL,
  "address" varchar NOT NULL,
  "area" float NOT NULL,
  "capacity" integer NOT NULL
);

CREATE TABLE "Lock" (
  "id" bigserial PRIMARY KEY,
  "barCode" varchar NOT NULL,
  "bikeId" bigint,
  "dockId" bigint NOT NULL,
  "status" "LOCK_STATUS" NOT NULL DEFAULT 'RELEASED'
);

CREATE TABLE "Rental" (
  "id" bigserial PRIMARY KEY,
  "bikeId" bigint NOT NULL,
  "rentDockId" bigint NOT NULL,
  "rentLockId" bigint NOT NULL,
  "startTime" timestamptz NOT NULL
);

CREATE TABLE "Return" (
  "id" bigserial PRIMARY KEY,
  "rentalId" bigint NOT NULL,
  "returnDockId" bigint NOT NULL,
  "returnLockId" bigint NOT NULL,
  "returnTime" timestamptz NOT NULL
);

CREATE TABLE "Invoice" (
  "id" bigserial PRIMARY KEY,
  "rid" bigint NOT NULL,
  "rtype" "INVOICE_OF" NOT NULL,
  "totalAmount" integer NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT (now())
);

CREATE TABLE "PaymentTransaction" (
  "id" bigserial PRIMARY KEY,
  "content" varchar NOT NULL DEFAULT '',
  "method" varchar NOT NULL DEFAULT 'credit card',
  "cardId" integer NOT NULL,
  "invoiceId" integer NOT NULL,
  "createdAt" timestamptz NOT NULL DEFAULT (now())
);

CREATE TABLE "Card" (
  "id" bigserial PRIMARY KEY,
  "cardCode" varchar NOT NULL,
  "owner" varchar NOT NULL,
  "cvvCode" varchar NOT NULL,
  "expiryDate" timestamp NOT NULL,
  "balance" bigint NOT NULL
);

COMMENT ON COLUMN "Bike"."price" IS 'must ge greater than 0';

COMMENT ON COLUMN "EBike"."batteryLife" IS 'must be greater than 0';

COMMENT ON COLUMN "Dock"."area" IS 'must be greater than 0';

COMMENT ON COLUMN "Dock"."capacity" IS 'must be greater than 0';

COMMENT ON COLUMN "Rental"."deposit" IS 'must be greater than 0';

COMMENT ON COLUMN "Return"."returnTime" IS 'must be later than startTime';

COMMENT ON COLUMN "Invoice"."rid" IS 'id of corresponding rental or return';

COMMENT ON COLUMN "Invoice"."totalAmount" IS 'must be greater than 0';

COMMENT ON COLUMN "Card"."balance" IS 'must be greater than 0';

ALTER TABLE "EBike" ADD FOREIGN KEY ("id") REFERENCES "Bike" ("id");

ALTER TABLE "Lock" ADD FOREIGN KEY ("dockId") REFERENCES "Dock" ("id");

ALTER TABLE "Rental" ADD FOREIGN KEY ("bikeId") REFERENCES "Bike" ("id");

ALTER TABLE "Rental" ADD FOREIGN KEY ("rentDockId") REFERENCES "Dock" ("id");

ALTER TABLE "Rental" ADD FOREIGN KEY ("rentLockId") REFERENCES "Lock" ("id");

ALTER TABLE "Return" ADD FOREIGN KEY ("rentalId") REFERENCES "Rental" ("id");

ALTER TABLE "Return" ADD FOREIGN KEY ("returnDockId") REFERENCES "Dock" ("id");

ALTER TABLE "Return" ADD FOREIGN KEY ("returnLockId") REFERENCES "Lock" ("id");

ALTER TABLE "PaymentTransaction" ADD FOREIGN KEY ("cardId") REFERENCES "Card" ("id");

ALTER TABLE "PaymentTransaction" ADD FOREIGN KEY ("invoiceId") REFERENCES "Invoice" ("id");