import express from 'express';

const app = express();
const port = 8080;
const host = '0.0.0.0';

app.get('/', (req, res) => {
  res.send('Hello World!');
});

app.listen(port, host, () => {
  console.log(`App listening at port ${port}`);
});
