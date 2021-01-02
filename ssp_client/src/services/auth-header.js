export default function authHeader(path) {
  const user = JSON.parse(localStorage.getItem('user'));

  if (user && user.access_token) {
    return { Authorization: 'Bearer ' + user.access_token,"Path":path }; // for Spring Boot back-end
    //return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
  } else {
    return {};
  }
}
