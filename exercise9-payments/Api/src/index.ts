import express from 'express';
import { Stripe } from 'stripe';

const port = 3000;
const host = '0.0.0.0';
const stripeSecretKey = process.env.STRIPE_SECRET_KEY;
if (!stripeSecretKey) throw new Error('Stripe Secret Key not found');

const app = express();
const stripe = new Stripe(stripeSecretKey);

app.get('/', (req, res) => {
  res.send('The server is up!');
});

app.post('/connection_token', async (req, res) => {
  const token = await stripe.terminal.connectionTokens.create();
  res.json({ secret: token.secret });
});

app.listen(port, host, () => {
  console.log(`Running on port ${port}`);
});
