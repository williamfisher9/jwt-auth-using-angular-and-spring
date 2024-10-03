const backendPort = 8080;

export const baseUrls = {
    register: `http://localhost:${backendPort}/api/v1/public/auth/register`,
    login: `http://localhost:${backendPort}/api/v1/public/auth/login`,
    dashboard: `http://localhost:${backendPort}/api/v1/app/dashboard`,
};