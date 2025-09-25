import os 
from dotenv import load_dotenv
import pytest
from playwright.sync_api import sync_playwright;

load_dotenv(dotenv_path="../.env")
@pytest.fixture(scope="session")
def baseurl():
    return os.getenv("BASE_URL", "https://the-internet.herokuapp.com")

@pytest.fixture(scope="session")
def pw_context():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=os.getenv("HEADLESS", "true").lower() == "true")
        context = browser.new_context(record_video_dir="test-artifacts/videos/")
        yield context
        context.tracking.stop(path="test-artifacts/trace.zip", screenshots=True, snapshots=True)
        browser.close()
    