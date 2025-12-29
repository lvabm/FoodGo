# Fix Compilation Errors

## Problem
The test files are failing to compile because Maven cannot find the main source code classes. This happens when:
1. Main source code hasn't been compiled yet
2. Maven tries to compile tests before main source

## Solution

### Option 1: Use the batch script (Windows)
```bash
cd backend
compile-fix.bat
```

### Option 2: Manual compilation
```bash
cd backend

# Step 1: Clean and compile main source code (skip tests)
mvn clean compile -DskipTests

# Step 2: Compile tests
mvn test-compile

# Step 3: Run tests (optional)
mvn test
```

### Option 3: Full build
```bash
cd backend
mvn clean install -DskipTests
```

## Why this happens
Maven compiles in phases:
1. `compile` - compiles main source code
2. `test-compile` - compiles test code (requires main classes to exist)

If you run `mvn test` or `mvn test-compile` directly without compiling main source first, you'll get "package does not exist" errors.

## IDE Solution
If using IntelliJ IDEA or Eclipse:
1. Right-click on `backend` folder
2. Select "Maven" > "Reload Project"
3. Then "Maven" > "Compile"
4. Finally "Maven" > "Test Compile"

