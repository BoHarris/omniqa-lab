from selenium.webdriver.common.by import By
import json
from pathlib import Path
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
    
def load_user_data():
    #__file_path = Path(__file__).parents / "shared" / "models" / "user.json"
    repo_root = Path(__file__).resolve().parents[3]
    return json.loads((repo_root / "shared" / "models" / "user.json").read_text(encoding=   "utf-8"))
    
def test_login_and_out_success(driver, base_url):
    user = load_user_data()
    if not user.get("username") or not user.get("password"):
        user = {"username": "tomsmith", "password": "SuperSecretPassword!"}
    driver.get(base_url + "/login")
    driver.find_element(By.ID, "username").send_keys(user["username"])
    driver.find_element(By.ID, "password").send_keys(user["password"])
    driver.find_element(By.CSS_SELECTOR, "button[type='submit']").click()
    WebDriverWait(driver, 10).until(
        lambda d: d.find_element(By.ID, "flash"))
    assert "You logged into a secure area!" in driver.page_source
    assert driver.current_url == base_url + "/secure"
    driver.find_element(By.CSS_SELECTOR, "a[href='/logout']").click()
    WebDriverWait(driver, 10).until(
        lambda d: d.find_element(By.ID, "flash"))
    assert "You logged out of the secure area!" in driver.page_source
    
def test_login_failure_user(driver, base_url):
    user = load_user_data()
    driver.get(base_url + "/login")
    driver.find_element(By.ID, "username").send_keys("invalid_user")
    driver.find_element(By.ID, "password").send_keys(user["password"])
    driver.find_element(By.CSS_SELECTOR, "button[type='submit']").click()
    WebDriverWait(driver, 10).until(
        lambda d: d.find_element(By.ID, "flash"))
    assert "Your username is invalid!" in driver.page_source
    WebDriverWait(driver, 5).until(lambda d: d.current_url.startswith(f"{base_url}/login"))

    assert driver.current_url == base_url + "/login"
    
def test_login_failure_pass(driver, base_url):
    user = load_user_data()
    driver.get(base_url + "/login")
    driver.find_element(By.ID, "username").send_keys(user["username"])
    driver.find_element(By.ID, "password").send_keys("invalid_pass")
    driver.find_element(By.CSS_SELECTOR, "button[type='submit']").click()
    WebDriverWait(driver, 10).until(
        lambda d: d.find_element(By.ID, "flash"))
    assert "Your password is invalid!" in driver.page_source
    WebDriverWait(driver, 5).until(lambda d: d.current_url.startswith(f"{base_url}/login"))
    assert driver.current_url == base_url + "/login"
    
def test_empty_credentials(driver, base_url):
    driver.get(base_url + "/login")
    driver.find_element(By.CSS_SELECTOR, "button[type='submit']").click()
    WebDriverWait(driver, 10).until(
        lambda d: d.find_element(By.ID, "flash"))
    assert "Your username is invalid!" in driver.page_source
    driver.save_screenshot("empty_credentials.png")
    assert driver.current_url == base_url + "/login"

