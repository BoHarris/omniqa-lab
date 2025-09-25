import os, requests

def test_users_returns_200_and_list():
    base = os.getenv("API_BASE_URL", "https://jsonplaceholder.typicode.com")
    r = requests.get(f"{base}/users", timeout=10)
    assert r.status_code == 200 and isinstance(r.json(), list)  and len(r.json()) > 0