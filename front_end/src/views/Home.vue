<template>
  <div id="home">
    <v-row v-if="this.$store.state.isLogin">
      <v-col cols="12" md="auto" sm="auto">
        <v-card>
          <v-card-title>
            <v-icon class="mr-2">mdi-calendar-month</v-icon>
            ToDo List
          </v-card-title>
          <v-list>
            <v-list-item>
              <v-list-item-title>Home</v-list-item-title>
            </v-list-item>

            <v-list-group>
              <template v-slot:activator>
                <v-list-item-title>Users</v-list-item-title>
              </template>
              <v-list-item>
                <v-list-item-title>fkdjs</v-list-item-title>
              </v-list-item>
            </v-list-group>
            <v-list-group>
              <template v-slot:activator>
                <v-list-item-title>Users</v-list-item-title>
              </template>
              <v-list-item>
                <v-list-item-title>fkdjs</v-list-item-title>
              </v-list-item>
            </v-list-group>
          </v-list>
        </v-card>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col>
        <v-card>
          <ProblemList></ProblemList>
        </v-card>
      </v-col>
    </v-row>
    <div v-else>
      <v-row>
        <v-col>
          <v-card min-height="40vh" to="/problems">
            <v-card-title>
              problem
            </v-card-title>
          </v-card>
        </v-col>
        <v-col>
          <v-card min-height="40vh" to="/contests">
            <v-card-title>
              contest
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card min-height="40vh" to="statu">
            <v-card-title>
              status
            </v-card-title>
          </v-card>
        </v-col>
        <v-col>
          <v-card min-height="40vh" to="ranking">
            <v-card-title>
              ranking
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import ProblemList from '@/components/problem_list.vue'

export default {
  name: 'home',
  components: {
    ProblemList
  },
  data: () => ({
    admins: [
      ['Management', 'people_outline'],
      ['Settings', 'settings']
    ],
    cruds: [
      ['Create', 'add'],
      ['Read', 'insert_drive_file'],
      ['Update', 'update'],
      ['Delete', 'delete']
    ]
  })
}
</script>
