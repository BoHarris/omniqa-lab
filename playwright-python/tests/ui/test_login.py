import json, os
from pathlib import Path
from playwright.sync_api import expect
import re

r = re.compile

def load_user():
    user_path = Path("../shared/user.json")
    if not user_path.exists():
        raise FileNotFoundError(f"User file not found: {user_path}")
    with user_path.open() as f:
        return json.load(f)

def ensure_artifacts_dir():
    artifacts_path = Path("test-artifacts/screenshots")
    artifacts_path.mkdir(parents=True, exist_ok=True)
    

def test_login_success(pw_context, base_url):
    ensure_artifacts_dir()
    page = pw_context.new_page()
    user = load_user()
    
    page.goto(f"{base_url}/login")
    page.fill("input[name='username']", user["username"])
    page.fill("input[name='password']", user["password"])
    page.click("button[type='submit']")
    
    expect(page.locator("#flash")).to_contain_text("You logged into a secure area!", timeout=5000) 
    page.screenshot(path="test-artifacts/screenshots/login_success.png")
    
    page.click("a[href='/logout']")
    expect(page).to_have_url(lambda url: url.endswith("/login"))
    
def test_login_failure_bad_username(pw_context, base_url):
    ensure_artifacts_dir()
    page = pw_context.new_page()
    user = load_user()
    
    page.goto(f"{base_url}/login")
    page.fill("input[name='username']", "notARealUser")
    page.fill("input[name='password']", user["password"])
    page.click("button[type='submit']")
    
  
    expect(page.locator("#flash")).to_contain_text(r"invalid!", timeout=5000)
    page.screenshot(path="test-artifacts/screenshots/login_invalid_username.png")
    
def test_login_failure_Bad_password(pw_context, base_url):
    ensure_artifacts_dir()
    page = pw_context.new_page()
    user = load_user()
    
    page.goto(f"{base_url}/login")
    page.fill("input[name='username']", user["username"])
    page.fill("input[name='password']", "wrongPassword123")
    page.click("button[type='submit']")

    expect(page.locator("#flash")).to_contain_text(r"invalid!", timeout=5000)
    page.screenshot(path="test-artifacts/screenshots/login_invalid_password.png")