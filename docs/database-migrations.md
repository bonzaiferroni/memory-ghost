# Database migration
source: https://developer.android.com/training/data-storage/room/migrating-db-versions

## Automatic migrations

```kt
// Database class before the version update.
@Database(
  version = 1,
  entities = [User::class]
)
abstract class AppDatabase : RoomDatabase() {
  ...
}

// Database class after the version update.
@Database(
  version = 2,
  entities = [User::class],
  autoMigrations = [
    AutoMigration (from = 1, to = 2)
  ]
)
abstract class AppDatabase : RoomDatabase() {
  ...
}
```

Note: Automated Room migrations rely on the generated database schema for both the old and the new versions of the database. If exportSchema is set to false, or if you have not yet compiled the database with the new version number, then automated migrations fail.

## Automatic migration specifications

Most commonly, this occurs when a migration involves one of the following:

    Deleting or renaming a table.
    Deleting or renaming a column.

Define a static class that implements AutoMigrationSpec in your RoomDatabase class and annotate it with one or more of the following:

    @DeleteTable
    @RenameTable
    @DeleteColumn
    @RenameColumn

```kt
@Database(
  version = 2,
  entities = [User::class],
  autoMigrations = [
    AutoMigration (
      from = 1,
      to = 2,
      spec = AppDatabase.MyAutoMigration::class
    )
  ]
)
abstract class AppDatabase : RoomDatabase() {
  @RenameTable(fromTableName = "User", toTableName = "AppUser")
  class MyAutoMigration : AutoMigrationSpec
  ...
}
```

## Manual
A Migration class explicitly defines a migration path between a startVersion and an endVersion by overriding the Migration.migrate() method. Add your Migration classes to your database builder using the addMigrations() method:

```kt
val MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
      "PRIMARY KEY(`id`))")
  }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
  }
}

Room.databaseBuilder(applicationContext, MyDb::class.java, "database-name")
  .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
```

## Gracefully handle missing migration paths
If Room can't find a migration path to upgrade an existing database on a device to the current version, an IllegalStateException occurs. If it is acceptable to lose existing data when a migration path is missing, call the fallbackToDestructiveMigration() builder method when you create the database:

```kt
Room.databaseBuilder(applicationContext, MyDb::class.java, "database-name")
        .fallbackToDestructiveMigration()
        .build()
```

If you only want to Room to fall back to destructive recreation in certain situations, there are a few alternatives to fallbackToDestructiveMigration():

    If specific versions of your schema history cause errors that you can't solve with migration paths, use fallbackToDestructiveMigrationFrom() instead. This method indicates that you want Room to fall back to destructive recreation only when migrating from specific versions.
    If you want Room to fall back to destructive recreation only when migrating from a higher database version to a lower one, use fallbackToDestructiveMigrationOnDowngrade() instead.

