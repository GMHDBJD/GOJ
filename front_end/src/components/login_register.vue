<template>
  <div id="login_register">
    <v-card>
      <v-card-text>
        <v-btn class="float-right" icon @click="$emit('close')">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-tabs centered v-model="tabs">
          <v-tab>Login</v-tab>
          <v-tab>Register</v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs">
          <v-tab-item transition="false" reverse-transition="false">
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-text-field label="Username"></v-text-field>
              <v-text-field label="Password" type="password"></v-text-field>
              <v-btn block color="primary" class="mr-4">
                Login
              </v-btn>
            </v-form>
          </v-tab-item>
          <v-tab-item transition="false" reverse-transition="false">
            <v-card-text>
              <v-text-field label="Username"></v-text-field>
              <v-text-field label="Password" type="password"></v-text-field>
              <v-text-field
                label="Password again"
                type="password"
              ></v-text-field>
              <v-text-field label="Email"></v-text-field>
              <v-btn block color="primary" class="mr-4">
                Login
              </v-btn>
            </v-card-text>
          </v-tab-item>
        </v-tabs-items>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    tabs: null
  })
}
</script>

<style></style>
