import express from 'express';
import { Stripe } from 'stripe';

const PORT = 3000;
const HOST = '0.0.0.0';
const STRIPE_PUBLIC_KEY = process.env.STRIPE_PUBLIC_KEY;
const STRIPE_SECRET_KEY = process.env.STRIPE_SECRET_KEY;
if (!STRIPE_PUBLIC_KEY || !STRIPE_SECRET_KEY)
  throw new Error('Stripe keys are missing');

const app = express();
const stripe = new Stripe(STRIPE_SECRET_KEY);

app.use(express.json());

app.get('/', (req, res) => {
  console.log('Server is up');
  res.send('Server is up!');
});

app.post('/payment-sheet', async (req, res) => {
  console.log('Creating payment sheet');

  let totalPrice = 0;
  for (const p of req.body.products) {
    totalPrice += p.price * p.quantity;
  }

  const customer = await stripe.customers.create();
  const ephemeralKey = await stripe.ephemeralKeys.create(
    { customer: customer.id },
    { apiVersion: '2020-03-02' },
  );
  const paymentIntent = await stripe.paymentIntents.create({
    amount: totalPrice,
    currency: 'pln',
    customer: customer.id,
    automatic_payment_methods: {
      enabled: true,
    },
  });

  res.json({
    paymentIntent: paymentIntent.client_secret,
    ephemeralKey: ephemeralKey.secret,
    customer: customer.id,
    publishableKey: STRIPE_PUBLIC_KEY,
  });
});

app.listen(PORT, HOST, () => {
  console.log(`Server running on port ${PORT}`);
});
