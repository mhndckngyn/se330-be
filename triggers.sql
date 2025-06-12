create or replace function update_timestamp() returns trigger
    language plpgsql
as
$$
BEGIN
   NEW."updatedat" = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$;

alter function update_timestamp() owner to ducminh;

create or replace function update_budget_on_expense_insert() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "budget"
    SET "current" = "current" + NEW."amount"
    WHERE "enddate" >= NEW."createdat"
      AND "startdate" <= NEW."createdat"
      AND ("categoryid" IS NULL OR "categoryid" = NEW."categoryid")
      AND "userid" = (
          SELECT "userid"
          FROM "account"
          WHERE "id" = NEW."accountid"
      );

    RETURN NEW;
END;
$$;

alter function update_budget_on_expense_insert() owner to ducminh;

create or replace function update_budget_on_expense_delete() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "budget"
    SET "current" = "current" - OLD."amount"
    WHERE "enddate" >= OLD."createdat"
      AND "startdate" <= OLD."createdat"
      AND ("categoryid" IS NULL OR "categoryid" = OLD."categoryid")
      AND "userid" = (
          SELECT "userid"
          FROM "account"
          WHERE "id" = OLD."accountid"
      );

    RETURN OLD;
END;
$$;

alter function update_budget_on_expense_delete() owner to ducminh;

create or replace function update_budget_on_expense_update() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "budget"
    SET "current" = "current" - OLD."amount"
    WHERE "enddate" >= OLD."createdat"
      AND "startdate" <= OLD."createdat"
      AND ("categoryid" IS NULL OR "categoryid" = OLD."categoryid")
      AND "userid" = (
          SELECT "userid"
          FROM "account"
          WHERE "id" = OLD."accountid"
      );

    UPDATE "budget"
    SET "current" = "current" + NEW."amount"
    WHERE "enddate" >= NEW."createdat"
      AND "startdate" <= NEW."createdat"
      AND ("categoryid" IS NULL OR "categoryid" = NEW."categoryid")
      AND "userid" = (
          SELECT "userid"
          FROM "account"
          WHERE "id" = NEW."accountid"
      );

    RETURN NEW;
END;
$$;

alter function update_budget_on_expense_update() owner to ducminh;

create or replace function update_account_on_income_insert() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "account"
    SET "balance" = "balance" + NEW."amount"
    WHERE "id" = NEW."accountid";

    RETURN NEW;
END;
$$;

alter function update_account_on_income_insert() owner to ducminh;

create or replace function update_account_on_income_update() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW."accountid" != OLD."accountid" THEN
        UPDATE "account"
        SET "balance" = "balance" - OLD."amount"
        WHERE "id" = OLD."accountid";

        UPDATE "account"
        SET "balance" = "balance" + NEW."amount"
        WHERE "id" = NEW."accountid";
    ELSE
        UPDATE "account"
        SET "balance" = "balance" + (NEW."amount" - OLD."amount")
        WHERE "id" = NEW."accountid";
    END IF;

    RETURN NEW;
END;
$$;

alter function update_account_on_income_update() owner to ducminh;

create or replace function update_account_on_income_delete() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "account"
    SET "balance" = "balance" - OLD."amount"
    WHERE "id" = OLD."accountid";

    RETURN OLD;
END;
$$;

alter function update_account_on_income_delete() owner to ducminh;

create or replace function update_account_on_expense_insert() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "account"
    SET "balance" = "balance" - NEW."amount"
    WHERE "id" = NEW."accountid";

    RETURN NEW;
END;
$$;

alter function update_account_on_expense_insert() owner to ducminh;

create or replace function update_account_on_expense_update() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW."accountid" != OLD."accountid" THEN
        UPDATE "account"
        SET "balance" = "balance" + OLD."amount"
        WHERE "id" = OLD."accountid";

        UPDATE "account"
        SET "balance" = "balance" - NEW."amount"
        WHERE "id" = NEW."accountid";
    ELSE
        UPDATE "account"
        SET "balance" = "balance" - (NEW."amount" - OLD."amount")
        WHERE "id" = NEW."accountid";
    END IF;

    RETURN NEW;
END;
$$;

alter function update_account_on_expense_update() owner to ducminh;

create or replace function update_account_on_expense_delete() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "account"
    SET "balance" = "balance" + OLD."amount"
    WHERE "id" = OLD."accountid";

    RETURN OLD;
END;
$$;

alter function update_account_on_expense_delete() owner to ducminh;

create or replace function update_accounts_on_transfer_insert() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "account"
    SET "balance" = "balance" - NEW."amount"
    WHERE "id" = NEW."sourceaccountid";

    UPDATE "account"
    SET "balance" = "balance" + NEW."amount"
    WHERE "id" = NEW."targetaccountid";

    RETURN NEW;
END;
$$;

alter function update_accounts_on_transfer_insert() owner to ducminh;

create or replace function update_accounts_on_transfer_update() returns trigger
    language plpgsql
as
$$
BEGIN
    -- Case 1: SourceAccountId and TargetAccountId both change
    IF NEW."sourceaccountid" != OLD."sourceaccountid" AND NEW."targetaccountid" != OLD."targetaccountid" THEN
        -- Restore the old source account
        UPDATE "account"
        SET "balance" = "balance" + OLD."amount"
        WHERE "id" = OLD."sourceaccountid";

        -- Reverse the old target account
        UPDATE "account"
        SET "balance" = "balance" - OLD."amount"
        WHERE "id" = OLD."targetaccountid";

        -- Subtract from the new source account
        UPDATE "account"
        SET "balance" = "balance" - NEW."amount"
        WHERE "id" = NEW."sourceaccountid";

        -- Add to the new target account
        UPDATE "account"
        SET "balance" = "balance" + NEW."amount"
        WHERE "id" = NEW."targetaccountid";

    -- Case 2: Only SourceaccountId changes
    ELSIF NEW."sourceaccountid" != OLD."sourceaccountid" THEN
        -- Restore the old source account
        UPDATE "account"
        SET "balance" = "balance" + OLD."amount"
        WHERE "id" = OLD."sourceaccountid";

        -- Subtract from the new source account
        UPDATE "account"
        SET "balance" = "balance" - NEW."amount"
        WHERE "id" = NEW."sourceaccountid";

    -- Case 3: Only TargetaccountId changes
    ELSIF NEW."targetaccountid" != OLD."targetaccountid" THEN
        -- Reverse the old target account
        UPDATE "account"
        SET "balance" = "balance" - OLD."amount"
        WHERE "id" = OLD."targetaccountid";

        -- Add to the new target account
        UPDATE "account"
        SET "balance" = "balance" + NEW."amount"
        WHERE "id" = NEW."targetaccountid";

    -- Case 4: Only Amount changes
    ELSIF NEW."amount" != OLD."amount" THEN
        -- Adjust the source account
        UPDATE "account"
        SET "balance" = "balance" + (OLD."amount" - NEW."amount")
        WHERE "id" = OLD."sourceaccountid";

        -- Adjust the target account
        UPDATE "account"
        SET "balance" = "balance" + (NEW."amount" - OLD."amount")
        WHERE "id" = OLD."targetaccountid";
    END IF;

    -- Return the updated row
    RETURN NEW;
END;
$$;

alter function update_accounts_on_transfer_update() owner to ducminh;

create or replace function update_accounts_on_transfer_delete() returns trigger
    language plpgsql
as
$$
BEGIN
    UPDATE "account"
    SET "balance" = "balance" + OLD."amount"
    WHERE "id" = OLD."sourceaccountid";

    UPDATE "account"
    SET "balance" = "balance" - OLD."amount"
    WHERE "id" = OLD."targetaccountid";

    RETURN OLD;
END;
$$;

alter function update_accounts_on_transfer_delete() owner to ducminh;

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON "user"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON "account"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON "income"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON "budget"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_updated_at
BEFORE UPDATE ON "transfer"
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER income_insert_trigger
AFTER INSERT ON "income"
FOR EACH ROW
EXECUTE FUNCTION update_account_on_income_insert();

CREATE TRIGGER income_update_trigger
AFTER UPDATE ON "income"
FOR EACH ROW
EXECUTE FUNCTION update_account_on_income_update();

CREATE TRIGGER income_delete_trigger
AFTER DELETE ON "income"
FOR EACH ROW
EXECUTE FUNCTION update_account_on_income_delete();

CREATE TRIGGER expense_insert_trigger
AFTER INSERT ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_account_on_expense_insert();

CREATE TRIGGER expense_update_trigger
AFTER UPDATE ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_account_on_expense_update();

CREATE TRIGGER expense_delete_trigger
AFTER DELETE ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_account_on_expense_delete();

CREATE TRIGGER transfer_insert_trigger
AFTER INSERT ON "transfer"
FOR EACH ROW
EXECUTE FUNCTION update_accounts_on_transfer_insert();

CREATE TRIGGER transfer_update_trigger
AFTER UPDATE ON "transfer"
FOR EACH ROW
EXECUTE FUNCTION update_accounts_on_transfer_update();

CREATE TRIGGER transfer_delete_trigger
AFTER DELETE ON "transfer"
FOR EACH ROW
EXECUTE FUNCTION update_accounts_on_transfer_delete();

CREATE TRIGGER expense_insert_trigger_budget
AFTER INSERT ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_budget_on_expense_insert();

CREATE TRIGGER expense_delete_trigger_budget
AFTER DELETE ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_budget_on_expense_delete();

CREATE TRIGGER expense_update_trigger_budget
AFTER UPDATE ON "expense"
FOR EACH ROW
EXECUTE FUNCTION update_budget_on_expense_update();