# 📦 Complete GitHub Setup Guide

## Prerequisites
- Git installed on your computer
- GitHub account created
- Project files ready

---

## Method 1: Using GitHub Web Interface + Git Commands (Recommended)

### Step 1: Create Repository on GitHub

1. Go to https://github.com
2. Click the **"+"** icon in top right corner
3. Select **"New repository"**
4. Fill in the details:
   - **Repository name**: `amdox-task-management`
   - **Description**: `Full-stack Task Management System with Spring Boot`
   - **Visibility**: Choose Public or Private
   - **DO NOT** check "Initialize with README" (we already have one)
5. Click **"Create repository"**

### Step 2: Navigate to Your Project

Open terminal/command prompt and navigate to your project directory:
```bash
cd path/to/amdox-task-management
```

### Step 3: Initialize Git Repository
```bash
# Initialize git
git init

# Check git status
git status
```

### Step 4: Add All Files
```bash
# Add all files to staging
git add .

# Or add specific files
git add pom.xml
git add src/
git add README.md
```

### Step 5: Create First Commit
```bash
git commit -m "Initial commit: Amdox Task Management System with Spring Boot

Features:
- JWT Authentication
- Role-based access control (Admin, Editor, Viewer)
- Task assignment and prioritization
- Real-time notifications
- Email reminders
- Comments and collaboration
- RESTful APIs"
```

### Step 6: Add Remote Repository

Replace `YOUR_USERNAME` with your actual GitHub username:
```bash
git remote add origin https://github.com/YOUR_USERNAME/amdox-task-management.git
```

Verify remote was added:
```bash
git remote -v
```

### Step 7: Push to GitHub
```bash
# Rename branch to main (if needed)
git branch -M main

# Push to GitHub
git push -u origin main
```

If this is your first time, you'll be prompted to login to GitHub.

---

## Method 2: Using GitHub CLI (Alternative)

### Install GitHub CLI

**macOS:**
```bash
brew install gh
```

**Windows:**
```bash
winget install --id GitHub.cli
```

**Linux:**
```bash
sudo apt install gh
```

### Authenticate
```bash
gh auth login
```

### Create and Push Repository
```bash
# Navigate to project
cd path/to/amdox-task-management

# Initialize git
git init
git add .
git commit -m "Initial commit"

# Create and push repository
gh repo create amdox-task-management --public --source=. --push
```

---

## Method 3: Using GitHub Desktop (GUI)

1. Download GitHub Desktop: https://desktop.github.com/
2. Install and login to GitHub
3. Click **File** → **Add Local Repository**
4. Select your project folder
5. Click **Publish repository**
6. Choose repository name and visibility
7. Click **Publish**

---

## 🔐 Authentication Options

### Option 1: HTTPS with Personal Access Token

1. Go to GitHub Settings → Developer settings → Personal access tokens
2. Click "Generate new token (classic)"
3. Select scopes: `repo`, `workflow`
4. Generate and copy the token
5. When pushing, use token as password

### Option 2: SSH Key (Recommended)
```bash
# Generate SSH key
ssh-keygen -t ed25519 -C "your_email@example.com"

# Start ssh-agent
eval "$(ssh-agent -s)"

# Add key to agent
ssh-add ~/.ssh/id_ed25519

# Copy public key
cat ~/.ssh/id_ed25519.pub
```

Then:
1. Go to GitHub Settings → SSH and GPG keys
2. Click "New SSH key"
3. Paste your public key
4. Click "Add SSH key"

Update remote URL:
```bash
git remote set-url origin git@github.com:YOUR_USERNAME/amdox-task-management.git
```

---

## 📝 Project Structure on GitHub

After pushing, your repository should look like this:
```
amdox-task-management/
├── .gitignore
├── README.md
├── QUICK_START.md
├── GITHUB_SETUP.md
├── pom.xml
├── Postman_Collection.json
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── amdox/
    │   │           └── taskmanagement/
    │   │               ├── config/
    │   │               ├── controller/
    │   │               ├── dto/
    │   │               ├── entity/
    │   │               ├── repository/
    │   │               ├── security/
    │   │               ├── service/
    │   │               └── AmdoxTaskManagementApplication.java
    │   └── resources/
    │       └── application.properties
    └── test/
```

---

## 🔄 Making Updates

### After Making Changes
```bash
# Check what changed
git status

# Add changes
git add .

# Commit with meaningful message
git commit -m "Add file upload feature for task attachments"

# Push to GitHub
git push origin main
```

### Create Feature Branch
```bash
# Create new branch
git checkout -b feature/add-dashboard

# Make changes and commit
git add .
git commit -m "Add dashboard with analytics"

# Push branch
git push origin feature/add-dashboard
```

Then create a Pull Request on GitHub.

---

## 🏷️ Add Topics and Description

After pushing, enhance your repository:

1. Go to your repository on GitHub
2. Click the gear icon (⚙️) next to "About"
3. Add description: `Full-stack Task Management System built with Spring Boot, JWT authentication, and role-based access control`
4. Add topics: `java`, `spring-boot`, `task-management`, `jwt`, `mysql`, `rest-api`, `maven`
5. Add website URL if deployed
6. Save changes

---

## 📋 Create Good README Badges

Add these badges to your README.md:
```markdown
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Build](https://img.shields.io/badge/Build-Passing-success)
```

---

## 🎯 Create Releases

### Tag a Version
```bash
# Create tag
git tag -a v1.0.0 -m "Initial release - Amdox Task Management v1.0.0"

# Push tag
git push origin v1.0.0
```

### Create Release on GitHub

1. Go to your repository
2. Click "Releases" → "Create a new release"
3. Choose the tag (v1.0.0)
4. Add release notes
5. Attach compiled JAR file (optional)
6. Publish release

---

## 📊 Enable GitHub Actions (Optional)

Create `.github/workflows/maven.yml`:
```yaml
name: Java CI with Maven

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Build with Maven
      run: mvn clean install -DskipTests
```

---

## 🔍 Add License

Create `LICENSE` file:

1. Go to repository on GitHub
2. Click "Add file" → "Create new file"
3. Name it `LICENSE`
4. Click "Choose a license template"
5. Select "MIT License"
6. Fill in your name
7. Commit

---

## ✅ Verification Checklist

After setup, verify:

- [ ] Repository is visible on GitHub
- [ ] All files are pushed
- [ ] README.md displays correctly
- [ ] .gitignore is working (no target/ or .idea/ folders)
- [ ] Repository has description and topics
- [ ] License is added
- [ ] QUICK_START.md is accessible

---

## 🆘 Troubleshooting

### Issue: "fatal: remote origin already exists"
```bash
git remote remove origin
git remote add origin https://github.com/YOUR_USERNAME/amdox-task-management.git
```

### Issue: "Updates were rejected"
```bash
git pull origin main --rebase
git push origin main
```

### Issue: Large files error
```bash
# Add to .gitignore
echo "target/" >> .gitignore
git rm -r --cached target/
git commit -m "Remove target directory"
git push origin main
```

### Issue: Authentication failed
- Use Personal Access Token instead of password
- Or setup SSH key authentication

---

## 🌟 Make Repository Look Professional

1. **Add Screenshots**: Create `screenshots/` folder with app images
2. **Add Demo**: Record a demo video and link it
3. **Add Contributing Guide**: Create `CONTRIBUTING.md`
4. **Add Issue Templates**: Create `.github/ISSUE_TEMPLATE/`
5. **Add Pull Request Template**: Create `.github/PULL_REQUEST_TEMPLATE.md`
6. **Enable Discussions**: Go to Settings → Features
7. **Add Wiki**: Document advanced features

---

## 📢 Share Your Project

After setup, share on:
- LinkedIn with hashtags: #Java #SpringBoot #WebDevelopment
- Twitter/X
- Reddit (r/java, r/programming)
- Dev.to
- Your portfolio website

---

## 🎉 Success!

Your project is now on GitHub! 

**Repository URL**: `https://github.com/YOUR_USERNAME/amdox-task-management`

Next steps:
1. ⭐ Star your own repository
2. 📝 Write about it on your blog
3. 🚀 Deploy to cloud (Heroku, AWS, Azure)
4. 📱 Build a mobile app
5. 🌐 Create a frontend with React/Angular

---

**Happy Coding! 🚀**
