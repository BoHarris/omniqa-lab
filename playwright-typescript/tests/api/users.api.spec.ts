import {test, expect} from '@playwright/test';
test('GET /users returns 200 and list of users', async ({request}) => {
    const res = await request.get((process.env.API_URL || 'https://jsonplaceholder.typicode.com') + '/users');
    expect(res.ok()).toBeTruthy();
    
    const json = await res.json();
    expect(Array.isArray(json)).toBeTruthy();
    expect(json.length).toBeGreaterThan(0);
});
