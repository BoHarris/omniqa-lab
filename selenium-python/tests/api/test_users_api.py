import os,requests

def test_users_api():
    base = os.getenv("USERS_API_BASE", "https://jsonplaceholder.typicode.com")
    r = requests.get(f"{base}/users", timeout=10)
    assert r.status_code == 200