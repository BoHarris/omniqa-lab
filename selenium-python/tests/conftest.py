import os, logging, json
from pathlib import Path
from dotenv import load_dotenv
import pytest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By

load_dotenv(dotenv_path="../.env")

logging.basicConfig(level=logging.INFO)
format='%(asctime)s - %(levelname)s - %(message)s'
logger = logging.getLogger(__name__)

@pytest.fixture(scope="session")
def base_url():
    return os.getenv("BASE_URL", "https://the-internet.herokuapp.com")

@pytest.fixture
def driver():
    opts = Options()
    if os.getenv("HEADLESS", "true").lower() == "true":
        opts.add_argument("--headless=new")
    driver = webdriver.Chrome(options=opts)
    yield driver
    driver.quit()   
    
@pytest.hookimpl(hookwrapper=True)
def pytest_runtest_makereport(item, call):
    outcome = yield
    rep = outcome.get_result()
    if rep.failed and "driver" in item.funcargs:
        d = item.funcargs["driver"]
        os.makedirs("report/screenshots", exist_ok=True)
        d.save_screenshot(f"report/screenshots/{item.name}.png")
        