# OmniQA Lab

OmniQA Lab is a milti-stack quality engineering portfolio that demostrates:
 - **UI automation** (Selenium & Playwright in Java, Python, Typescript)

 - **API testing** (Requests, RestAssured, Playwright, APIRequestContext)

 - **Performance testing** (JMeter/ Locust)

 - **Manual test design** (Markdown-style test cases)

 - **CI/CD publishing** with GitHub Actions (auto-generated html reports)

 The goal is to showcase a **unified QA skillset** across popular industry stacks while maintaining a **clean, modular structure**.

 ---

## 📂 Project Structure

omniqa-lab/
├─ README.md # This file
├─ shared/ # shared models, data, configs usable across stacks
│ ├─ models/ # e.g. User, Product schema definitions (JSON, YAML)
│ └─ testdata/ # sample CSV/JSON datasets
├─ manual-tests/ # human-readable test cases
│ └─ test-cases.md
├─ selenium-java/ # UI + API tests in Java
│ ├─ pom.xml
│ └─ src/test/java/
│ ├─ ui/ # selenium UI tests
│ └─ api/ # rest-assured API tests
├─ selenium-python/ # UI + API tests in Python
│ ├─ requirements.txt
│ └─ tests/
│ ├─ ui/ # selenium UI tests
│ └─ api/ # requests API tests
├─ playwright-python/ # UI + API tests in Python Playwright
│ ├─ requirements.txt
│ └─ tests/
│ ├─ ui/ # playwright UI tests
│ └─ api/ # playwright API tests
├─ playwright-typescript/ # UI + API tests in TS Playwright
│ ├─ package.json
│ ├─ playwright.config.ts
│ └─ tests/
│ ├─ ui/ # playwright UI tests
│ └─ api/ # playwright API tests
├─ performance-tests/ # load & stress scenarios
│ ├─ login_load.jmx
│ └─ README.md
└─ .github/
└─ workflows/
└─ ci.yml # GitHub Actions workflow


---

## 🔄 Shared Models & Data

- **Why?** Instead of rewriting test data or user models in each language, we define them once in `shared/`.
- **Format:** JSON/YAML for data (easily read by Java, Python, and TS).
- **Usage Examples:**
  - Java: use Jackson to parse `shared/models/user.json`
  - Python: use `json` module to load the same file
  - TypeScript: import JSON directly with Node/Playwright

This ensures **consistency** across all test stacks.

---

## 🚀 Running Tests

Each stack has its own README with setup instructions, but in general:

### Java (Selenium + API)
cd selenium-java
mvn test

Python (Selenium / Playwright / API)
cd selenium-python
pip install -r requirements.txt
pytest tests

Playwright TypeScript
cd playwright-typescript
npm install
npx playwright test

Performance (JMeter)
cd performance-tests
# Run inside Docker or locally with JMeter

📊 Reports

On each push, GitHub Actions runs every suite and publishes fresh HTML reports to GitHub Pages.

See: Reports (link once Pages is live)